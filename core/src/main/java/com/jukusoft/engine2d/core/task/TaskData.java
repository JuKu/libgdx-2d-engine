package com.jukusoft.engine2d.core.task;

import com.jukusoft.engine2d.core.utils.Handler;

import java.util.Objects;

public class TaskData {

    private Task task;
    private Handler<Void> onSuccessHandler;
    private Handler<Throwable> onExceptionHandler;

    public TaskData() {
        //
    }

    public void init (Task task, Handler<Void> onSuccessHandler, Handler<Throwable> onExceptionHandler) {
        Objects.requireNonNull(task);
        this.task = task;
        this.onSuccessHandler = onSuccessHandler;
        this.onExceptionHandler = onExceptionHandler;
    }

    public Task getTask() {
        return task;
    }

    public Handler<Void> getOnSuccessHandler() {
        return onSuccessHandler;
    }

    public Handler<Throwable> getOnExceptionHandler() {
        return onExceptionHandler;
    }

}
