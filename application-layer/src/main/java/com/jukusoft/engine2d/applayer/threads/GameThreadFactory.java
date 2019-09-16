package com.jukusoft.engine2d.applayer.threads;

import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.utils.Threads;

public class GameThreadFactory {

    private GameThreadFactory() {
        //
    }

    public static Thread createAndStartThread(int threadID, String threadName, ObjectArrayList<SubSystem> subSystemList) {
        Thread thread = new Thread(new GameThread(subSystemList, threadID));
        thread.setName(threadName);
        thread.start();

        //register thread, so thread will be interrupted automatically on game shutdown
        Threads.registerThread(threadID, thread);

        return thread;
    }

}
