package com.jukusoft.engine2d.core.utils;

import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.core.logger.Log;
import org.mini2Dx.gdx.utils.ObjectMap;

import java.util.function.Consumer;

public class Threads {

    private static int threadCount = 0;

    //thread IDs
    public static final int UI_THREAD = 1;
    public static final int LOGIC_THREAD = 2;
    public static final int NETWORK_THREAD = 3;

    private static ObjectMap<Integer,Thread> threads = new ObjectMap<>(10);

    private Threads() {
        //
    }

    public static int getThreadCount() {
        if (threadCount == 0) throw new IllegalStateException("Threads was not initialized with setThreadCount() before");

        return threadCount;
    }

    public static void setThreadCount(int threadCount) {
        if (threadCount <= 0) throw new IllegalArgumentException("threadCount has to be > 0");

        Threads.threadCount = threadCount;
    }

    public static void registerThread(int threadID, Thread thread) {
        if (threadID <= 0 || threadID > threadCount)
            throw new IllegalArgumentException("threadID is out of range (min: 1, max: " + threadCount + ", value: " + threadID + ")");

        threads.put(threadID, thread);
    }

    public static void unregisterThread(int threadID) {
        if (threadID <= 0 || threadID > threadCount)
            throw new IllegalArgumentException("threadID is out of range (min: 1, max: " + threadCount + ", value: " + threadID + ")");

        threads.remove(threadID);
    }

    public static void interruptAllThreads() {
        threads.forEach(entry -> {
            int threadID = entry.key;
            Thread thread = entry.value;

            Log.i("Threads", "interrupt thread " + threadID + " now");
            thread.interrupt();
        });
    }

}
