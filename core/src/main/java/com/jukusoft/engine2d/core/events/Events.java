package com.jukusoft.engine2d.core.events;

import com.jukusoft.engine2d.core.utils.Threads;

/**
 * class to queue and trigger events like EventManager, but in thread safe
 *
 * @see EventManager
 */
public class Events {

    protected static final int NUM_THREADS = Threads.getThreadCount();
    protected static EventManager[] managers;

    protected Events() {
        //
    }

    public static void init() {
        managers = new EventManager[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            managers[i] = new EventManager("event manager #" + i + "", false);
        }
    }

    public static void queueEvent(EventData event) {
        //increment reference counter
        event.retain(NUM_THREADS - 1);

        //add event to queues
        for (int i = 0; i < NUM_THREADS; i++) {
            if (managers[i] != null) {
                managers[i].queueEvent(event);
            }
        }
    }

    public static void triggerEvent(EventData event) {
        //increment reference counter
        event.retain(NUM_THREADS - 1);

        //call all event managers
        for (int i = 0; i < NUM_THREADS; i++) {
            managers[i].triggerEvent(event);
        }
    }

    public static void update(int threadID, int maxMillis) {
        if (threadID > NUM_THREADS) {
            throw new IllegalArgumentException("threadID cannot >= number of threads, but threadID: " + threadID + " >= " + NUM_THREADS);
        }

        //process events
        managers[threadID-1].update(maxMillis);
    }

    public static void addListener(int threadID, int typeID, EventListener listener) {
        if (threadID > NUM_THREADS) {
            throw new IllegalArgumentException("threadID cannot >= number of threads, but threadID: " + threadID + " >= " + NUM_THREADS);
        }

        managers[threadID-1].addListener(typeID, listener);
    }

    public static void removeListener(int threadID, int typeID, EventListener listener) {
        if (threadID > NUM_THREADS) {
            throw new IllegalArgumentException("threadID cannot >= number of threads, but threadID: " + threadID + " >= " + NUM_THREADS);
        }

        managers[threadID-1].removeListener(typeID, listener);
    }

}
