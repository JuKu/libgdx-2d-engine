package com.jukusoft.engine2d.applayer.init;

import com.jukusoft.engine2d.core.init.Initializer;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class InitializerProcessor {

    private final Queue<Initializer> queue;

    public InitializerProcessor(List<Initializer> list) {
        queue = new PriorityQueue<>((o1, o2) -> {
            int priority1 = 100;
            int priority2 = 100;

            if (o1.getClass().isAnnotationPresent(InitPriority.class)) {
                priority1 = o1.getClass().getAnnotation(InitPriority.class).value();
            }

            if (o2.getClass().isAnnotationPresent(InitPriority.class)) {
                priority2 = o2.getClass().getAnnotation(InitPriority.class).value();
            }

            return priority1 - priority2;
        });
        queue.addAll(list);
    }

    public void addTask(Initializer initializer) {
        this.queue.add(initializer);
    }

    public void processBeforeSplashScreen() throws Exception {
        for (Initializer initializer : this.queue) {
            if (initializer.getClass().isAnnotationPresent(InitBeforeSplashScreen.class)) {
                initializer.init();
            }
        }
    }

    /**
     * process one entry
     */
    public void process() throws Exception {
        //sortInitializers();

        if (!queue.isEmpty()) {
            Initializer initializer = queue.poll();

            //don't execute @InitBeforeSplashScreen initializers again
            if (!initializer.getClass().isAnnotationPresent(InitBeforeSplashScreen.class)) {
                initializer.init();
            }
        }
    }

    public boolean hasFinished() {
        return queue.isEmpty();
    }

}
