package com.jukusoft.engine2d.core.shutdown;

import com.jukusoft.engine2d.core.logger.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
* error handler manages shutdown. Is called, if the app crashes.
*/
public class ErrorHandler {

    private ErrorHandler () {
        //
    }

    public static void shutdownWithException (Throwable e) {
        e.printStackTrace();

        Logger.getAnonymousLogger().log(Level.SEVERE, "Exception while initializing game: ", e);
        Log.e("BaseApp", "Exception while initializing game engine: ", e);

        Log.shutdown();

        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.exit(0);
    }

}
