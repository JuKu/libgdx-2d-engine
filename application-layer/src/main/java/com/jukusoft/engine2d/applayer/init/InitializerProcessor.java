package com.jukusoft.engine2d.applayer.init;

import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class InitializerProcessor {

    private final Queue<Initializer> queue;

    public InitializerProcessor(List<Initializer> list) {
        queue = new PriorityQueue<>(new InitializerComperator());
        queue.addAll(list);
    }

    public void addTask(Initializer initializer) {
        this.queue.add(initializer);
    }

    public void processBeforeSplashScreen() throws Exception {
        Initializer[] initializerArray = this.queue.toArray(new Initializer[0]);
        Arrays.sort(initializerArray, new InitializerComperator());

        for (Initializer initializer : initializerArray) {
            if (initializer.getClass().isAnnotationPresent(InitBeforeSplashScreen.class)) {
                Log.d(InitializerProcessor.class.getSimpleName(), "execute initializer: " + initializer.getClass().getSimpleName());
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
                Log.d(InitializerProcessor.class.getSimpleName(), "execute initializer: " + initializer.getClass().getSimpleName());
                initializer.init();
            }
        }
    }

    public boolean hasFinished() {
        return queue.isEmpty();
    }

}
