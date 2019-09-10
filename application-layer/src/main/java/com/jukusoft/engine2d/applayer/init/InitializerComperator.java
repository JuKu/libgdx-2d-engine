package com.jukusoft.engine2d.applayer.init;

import com.jukusoft.engine2d.core.init.Initializer;

import java.util.Comparator;

public class InitializerComperator implements Comparator<Initializer> {

    @Override
    public int compare(Initializer o1, Initializer o2) {
        int priority1 = 100;
        int priority2 = 100;

        if (o1.getClass().isAnnotationPresent(InitPriority.class)) {
            priority1 = o1.getClass().getAnnotation(InitPriority.class).value();

            if (!o1.getClass().isAnnotationPresent(InitBeforeSplashScreen.class)) {
                priority1 += 1000;
            }
        }

        if (o2.getClass().isAnnotationPresent(InitPriority.class)) {
            priority2 = o2.getClass().getAnnotation(InitPriority.class).value();

            if (!o2.getClass().isAnnotationPresent(InitBeforeSplashScreen.class)) {
                priority2 += 1000;
            }
        }

        //return priority1 - priority2;
        return Integer.compare(priority1, priority2);
    }

}
