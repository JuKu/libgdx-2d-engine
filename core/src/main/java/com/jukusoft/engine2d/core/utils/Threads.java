package com.jukusoft.engine2d.core.utils;

public class Threads {

    private static int threadCount = 0;

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

}
