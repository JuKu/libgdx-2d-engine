package com.jukusoft.engine2d.test.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jukusoft.engine2d.applayer.BaseApp;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.Utils;

import java.io.File;
import java.util.Set;

public class DesktopLauncher {

    public static void main(String[] args) {
        for (String param : args) {
            if (param.startsWith("-DclearCache") || param.startsWith("-clearCache")) {
                System.setProperty("clearCache", "true");
            }
        }

        //start game
        try {
            start();
        } catch (Exception e) {
            System.out.println("Exception occurred, exit application now.");
            Log.e("DesktopLauncher", "Exception while startup proxy server: ", e);

            //try to shutdown logs first
            Log.shutdown();

            try {
                //wait while logs are written to file
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                //don't do anything here
            }

            e.printStackTrace();
            System.exit(0);
        }
    }

    protected static void start() throws Exception {
        //check working directory
        if (!(new File("./config/").exists())) {
            throw new IllegalStateException("Working directory isn't correct, couldn't found config directory! Current working dir: " + (new File(".").getAbsolutePath()));
        }

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Engine2D Test Window");
        config.setWindowedMode(1280, 720);
        config.setResizable(false);
        config.useVsync(true);

        // start game
        new Lwjgl3Application(new BaseApp(DesktopLauncher.class) {
            //
        }, config);

        //list currently active threads
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        //print log
        Utils.printSection("Shutdown");
        Log.i("Shutdown", "Shutdown now.");

        //shutdown logger and write all remaining logs to file
        Log.shutdown();

        //wait 200ms, so logs can be written to file
        Thread.sleep(200);

        //check, if there are other active threads, except the main thread
        if (threadSet.size() > 1) {
            System.err.println("Shutdown: waiting for active threads:");

            for (Thread thread : threadSet) {
                System.err.println(" - " + thread.getName());
            }

            //wait 3 seconds, then force shutdown
            Thread.sleep(2000);
        }

        System.err.println("shutdown JVM now.");

        //force JVM shutdown
        if (Config.forceExit) {
            System.exit(0);
        }
    }

}