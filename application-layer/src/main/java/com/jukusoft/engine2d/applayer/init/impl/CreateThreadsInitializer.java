package com.jukusoft.engine2d.applayer.init.impl;

import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.applayer.init.InitBeforeSplashScreen;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.applayer.threads.GameThreadFactory;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;
import com.jukusoft.engine2d.core.utils.Threads;

import java.util.Objects;

@InitPriority(10)
public class CreateThreadsInitializer implements Initializer {

    private static final String LOG_TAG = "CreateThreadsInit";

    private final SubSystemManager subSystemManager;

    public CreateThreadsInitializer(SubSystemManager subSystemManager) {
        Objects.requireNonNull(subSystemManager);
        this.subSystemManager = subSystemManager;
    }

    @Override
    public void init() throws Exception {
        //create game threads (exclude threadID 1, because this is the already existing ui thread)
        for (int i = 1; i < Threads.getThreadCount(); i++) {
            int threadID = i + 1;
            GameThreadFactory.createAndStartThread(threadID, "game-thread-" + threadID, this.subSystemManager.listSubSystemsByThread(threadID));
        }
    }

}
