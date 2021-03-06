package com.jukusoft.engine2d.core.utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Platform {

    protected static Queue<Runnable> queue = new ConcurrentLinkedQueue<>();

    protected Platform() {
        //
    }

    public static void runOnUIThread(Runnable runnable) {
        queue.add(runnable);
    }

    public static void executeQueue() {
        while (true) {
            Runnable runnable = queue.poll();

            if (runnable != null) {
                runnable.run();
            } else {
                break;
            }
        }
    }

    protected static void clearQueue() {
        queue.clear();
    }

    protected static int getQueueSize() {
        return queue.size();
    }

}
