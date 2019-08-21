package com.jukusoft.engine2d.core.task;

import com.jukusoft.engine2d.core.utils.Handler;

/**
* every task manager handles only one thread
*/
public interface TaskManager {

    /**
    * handle tasks
     *
     * @param maxMillis max time in which tasks should be handled, if timeout reached, remaining tasks will be handled in next frame
    */
    public void run (int maxMillis);

    public void addTask (Task task);

    public void addTask (Task task, Handler<Throwable> onExceptionHandler);

    public void addTask (Task task, Handler<Void> onSuccessHandler, Handler<Throwable> onExceptionHandler);

}
