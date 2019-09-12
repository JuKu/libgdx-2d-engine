package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitBeforeSplashScreen;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.Threads;

@InitPriority(10)
@InitBeforeSplashScreen
public class ThreadsInitializer implements Initializer {

    private static final String LOG_TAG = "ThreadsInit";

    @Override
    public void init() throws Exception {
        int threadCount = Config.getInt("Threads", "threadCount");
        Threads.setThreadCount(threadCount);

        Log.i(LOG_TAG, "number of game threads: " + threadCount);
    }

}
