package com.jukusoft.engine2d.applayer.init;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InitPriority {

    /**
     * get priority, if smaller, than the Initializer will be called earlier
     *
     * @return priority
     */
    int value() default 20;

}
