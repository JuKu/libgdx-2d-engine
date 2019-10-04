package com.jukusoft.engine2d.core.task;

import com.jukusoft.engine2d.core.task.TaskPriority;

import java.util.Comparator;

public class TaskPriorityComperator<T> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        int priority1 = getPriority(o1);
        int priority2 = getPriority(o2);

        return Integer.compare(priority2, priority1);
    }

    private void checkAnnotationPresent(T task) {
        if (!task.getClass().isAnnotationPresent(TaskPriority.class)) {
            throw new IllegalStateException("no task priority annotation found in class " + task.getClass().getSimpleName());
        }
    }

    private int getPriority(T task) {
        checkAnnotationPresent(task);
        return task.getClass().getAnnotation(TaskPriority.class).value();
    }

}
