package com.jukusoft.engine2d.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jukusoft.engine2d.applayer.BaseGame;
import com.jukusoft.engine2d.applayer.BaseGameFactory;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.FileUtils;
import com.jukusoft.engine2d.core.utils.ResourceUtils;
import com.jukusoft.engine2d.core.utils.SPIUtils;
import com.jukusoft.engine2d.core.utils.Utils;
import com.jukusoft.engine2d.desktop.config.WindowConfig;

import java.io.File;
import java.util.List;
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
            e.printStackTrace();

            System.out.println("Exception occurred, exit application now.");
            Log.e("DesktopLauncher", "Exception while startup game client: ", e);

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

        /*Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Engine2D Test Window");
        config.setWindowedMode(1280, 720);
        config.setResizable(false);
        config.useVsync(true);*/

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        if (!new File("config").exists()) {
            new File("config").mkdirs();
        }

        //create default content, if config/window.cfg doesn't exists
        FileUtils.createFileIfAbsent("./config/window.cfg", ResourceUtils.getResourceFileAsString("template/window.cfg"));
        FileUtils.createFileIfAbsent("./config/logger.cfg", ResourceUtils.getResourceFileAsString("template/logger.cfg"));

        //load window config
        WindowConfig windowConfig = new WindowConfig("./config/window.cfg");
        windowConfig.fillConfig(config);

        // start game
        new Lwjgl3Application(createBaseGameInstance(), config);

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

    private static BaseGame createBaseGameInstance() {
        List<BaseGameFactory> factories = SPIUtils.findImplementations(BaseGameFactory.class);

        if (factories.isEmpty()) {
            throw new IllegalStateException("no SPI factory is set: " + BaseGameFactory.class.getSimpleName() + ", this means game project is not well configured");
        }

        if (factories.size() > 1) {
            throw new IllegalStateException("multiple SPI implementations for " + BaseGameFactory.class.getSimpleName() + " found");
        }

        //allow user to set config values per properties, see #172165495
        for (String propertyName : System.getProperties().stringPropertyNames()) {
            String[] array = propertyName.split("\\.");

            if (array.length != 2) {
                //property does not contain section
                continue;
            }

            //don't parse system paramaters
            if (array[0].contains("sun") || array[0].contains("awt") || array[0].contains("user") || array[0].contains("os") || array[0].contains("jdk") || array[0].contains("java") || array[0].contains("file") || array[0].contains("line") || array[0].contains("path")) {
                continue;
            }

            String value = System.getProperty(propertyName);

            if (value != null && !value.isEmpty()) {
                Config.set(array[0], array[1], value);
            }
        }

        return factories.get(0).createGame();
    }

}
