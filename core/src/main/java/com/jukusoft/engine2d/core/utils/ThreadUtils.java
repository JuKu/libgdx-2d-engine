package com.jukusoft.engine2d.core.utils;

import com.jukusoft.engine2d.core.logger.Log;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadUtils {

    protected ThreadUtils() {
        //
    }

    public static void executeOnUIThreadAndWait(Runnable runnable) {
        AtomicBoolean b = new AtomicBoolean(false);

        com.jukusoft.engine2d.core.utils.Platform.runOnUIThread(() -> {
            runnable.run();

            b.set(true);
        });

        while (!b.get()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Log.w("ThreadUtils", "InterruptedException in method executeOnUIThreadAndWait(): ", e);
            }
        }
    }

}
