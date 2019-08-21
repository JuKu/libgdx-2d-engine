package com.jukusoft.engine2d.core.task.impl;

import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.memory.Pools;
import com.jukusoft.engine2d.core.task.Task;
import com.jukusoft.engine2d.core.task.TaskData;
import com.jukusoft.engine2d.core.task.TaskManager;
import com.jukusoft.engine2d.core.utils.Handler;
import org.mini2Dx.gdx.utils.AtomicQueue;
import org.mini2Dx.gdx.utils.Queue;

public class DefaultTaskManager implements TaskManager {

    private AtomicQueue<TaskData> taskQueue = new AtomicQueue<>(100);
    private final String name;

    public DefaultTaskManager(String name) {
        this.name = name;
    }

    @Override
    public void run(int maxMillis) {
        long startTime = System.currentTimeMillis();

        //TODO: free task data

        TaskData taskData = null;
        long endTime = 0;
        long diffTime = 0;

        //process tasks
        while ((taskData = taskQueue.poll()) != null) {
            executeTask(taskData);

            endTime = System.currentTimeMillis();
            diffTime = endTime - startTime;

            if (diffTime >= maxMillis) {
                //process other tasks in next frame
                break;
            }
        }
    }

    private void executeTask (TaskData taskData) {
        Task task = taskData.getTask();

        try {
            task.run();
            taskData.getOnSuccessHandler().handle(null);
        } catch (Exception e) {
            Log.w("Tasks", "Exception in task: " + task.getClass().getSimpleName(), e);
            e.printStackTrace();
            taskData.getOnExceptionHandler().handle(e);
        }

        //cleanup memory
        Pools.free(task);
        Pools.free(taskData);
    }

    @Override
    public void addTask(Task task) {
        addTask(task, null);
    }

    @Override
    public void addTask(Task task, Handler<Throwable> onExceptionHandler) {
        addTask(task, null, onExceptionHandler);
    }

    @Override
    public void addTask(Task task, Handler<Void> onSuccessHandler, Handler<Throwable> onExceptionHandler) {
        TaskData taskData = Pools.get(TaskData.class);
        taskData.init(task, onSuccessHandler, onExceptionHandler);
        taskQueue.put(taskData);
    }

}
