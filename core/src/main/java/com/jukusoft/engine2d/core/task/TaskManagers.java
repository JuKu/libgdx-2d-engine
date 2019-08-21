package com.jukusoft.engine2d.core.task;

import java.util.Objects;

public class TaskManagers {

    private static final int MAX_THREADS = 10;
    private static final TaskManager[] taskManagers = new TaskManager[MAX_THREADS];

    public static TaskManager get (int threadID) {
        if (threadID >= MAX_THREADS) {
            throw new IllegalArgumentException("thread with id " + threadID + " doesn't exists, threadID(" + threadID + ") > MAX_THREADS(" + MAX_THREADS + ")");
        }

        if (taskManagers[threadID] == null) {
            throw new IllegalStateException("task manager with threadID " + threadID + " doesn't exists");
        }

        return taskManagers[threadID];
    }

    public static void register (int threadID, TaskManager taskManager) {
        if (threadID < 0) throw new IllegalArgumentException("threadID has to be positive");
        if (threadID >= MAX_THREADS) throw new IllegalArgumentException("max allowed threadID is " + MAX_THREADS + ", current value: " + threadID);
        Objects.requireNonNull(taskManager);

        if (taskManagers[threadID] != null) throw new IllegalStateException("task manager with threadID " + threadID + " was already registered");

        taskManagers[threadID] = taskManager;
    }

}
