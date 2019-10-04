package com.jukusoft.engine2d.core.task;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TaskPriority {

    /**
     * get priority, if greater, than the task will be executed earlier
     *
     * @return priority
     */
    int value() default 50;

}
