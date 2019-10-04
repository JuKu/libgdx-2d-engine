package com.jukusoft.engine2d.core.task;

import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.task.TaskPriority;

import java.util.Comparator;

public class TaskPriorityComperator<T> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        int priority1 = getPriority(o1);
        int priority2 = getPriority(o2);

        System.out.println("priority1: " + priority1 + ", priority2: " + priority2);

        return Integer.compare(priority2, priority1);
    }

    @Deprecated
    private void checkAnnotationPresent(T task) {
        if (!task.getClass().isAnnotationPresent(TaskPriority.class)) {
            throw new IllegalStateException("no task priority annotation found in class " + task.getClass().getSimpleName());
        }
    }

    private int getPriority(T task) {
        return getPriorityByClass(task.getClass(), task.getClass());
    }

    private int getPriorityByClass(Class<?> cls, Class<?> originalClass) {
        //checkAnnotationPresent(task);
        if (!cls.isAnnotationPresent(TaskPriority.class)) {
            //check super classes
            if (cls.getSuperclass() != null) {
                return getPriorityByClass(cls.getSuperclass(), originalClass);
            }

            Log.w(TaskPriorityComperator.class.getSimpleName(), "no @TaskPriority annotation found for class: " + originalClass.getSimpleName());
            return TaskPriority.DEFAULT_VALUE;
        }

        return cls.getAnnotation(TaskPriority.class).value();
    }

}
