package com.jukusoft.engine2d.basegame.loading;

import com.jukusoft.engine2d.core.task.TaskPriorityComperator;

import java.util.PriorityQueue;
import java.util.Queue;

public class LoadingProcessor {

    private final Queue<LoadingTask> queue;

    public LoadingProcessor() {
        this.queue = new PriorityQueue<>(new TaskPriorityComperator<>());
    }

    public void addTask(LoadingTask task) {
        queue.add(task);
    }

    public void removeTask(LoadingTask task) {
        queue.remove(task);
    }

    /**
     * process only one task from queue
     *
     * @return true, if there is a task to process
     */
    public boolean process() throws Exception {
        if (!queue.isEmpty()) {
            LoadingTask task = queue.poll();
            task.load();
        }

        return !hasFinished();
    }

    /**
     * process multiple tasks from queue, but if a given time range
     *
     * @param maxMillis max time in ms to execute
     * @return true, if there are other tasks remaining to execute
     */
    public boolean process(int maxMillis) throws Exception {
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        long diffTime = 0;

        while (!hasFinished()) {
            process();

            endTime = System.currentTimeMillis();
            diffTime = endTime - startTime;

            if (diffTime >= maxMillis) {
                //process other tasks in next frame
                break;
            }
        }

        return !hasFinished();
    }

    public void processAll() throws Exception {
        while (!hasFinished()) {
            process();
        }
    }

    /**
     * check, if all tasks was executed
     *
     * @return true, if loading was finished
     */
    public boolean hasFinished() {
        return queue.isEmpty();
    }

}
