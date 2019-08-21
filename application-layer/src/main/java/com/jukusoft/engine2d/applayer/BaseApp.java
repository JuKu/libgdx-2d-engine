package com.jukusoft.engine2d.applayer;

import com.badlogic.gdx.ApplicationListener;
import com.jukusoft.engine2d.applayer.init.InitializerProcessor;
import com.jukusoft.engine2d.applayer.init.SplashScreenDrawer;
import com.jukusoft.engine2d.applayer.init.factory.InitializerProcessorFactory;
import com.jukusoft.engine2d.core.shutdown.ErrorHandler;
import com.jukusoft.engine2d.core.task.TaskManager;
import com.jukusoft.engine2d.core.utils.Platform;

public abstract class BaseApp implements ApplicationListener {

    private final Class<?> gameClass;
    private InitializerProcessor initProcessor;
    private SplashScreenDrawer splashScreenDrawer;
    private boolean initialized = false;

    public BaseApp(Class<?> gameClass) {
        this.gameClass = gameClass;
    }

    @Override
    public void create() {
        Thread.currentThread().setName("ui-thread");

        //initialize game engine
        this.initProcessor = InitializerProcessorFactory.create(this.gameClass);

        try {
            initProcessor.processBeforeSplashScreen();
        } catch (Exception e) {
            ErrorHandler.shutdownWithException(e);
        }

        this.splashScreenDrawer = new SplashScreenDrawer();
    }

    @Override
    public void resize(int width, int height) {
        if (!initialized) {
            return;
        }

        //TODO: fire event
    }

    @Override
    public void render() {
        //run tasks which have to be executed in ui thread
        Platform.executeQueue();

        if (!initialized) {
            //call initializers
            try {
                initProcessor.process();
            } catch (Exception e) {
                e.printStackTrace();
            }

            initialized = initProcessor.hasFinished();

            splashScreenDrawer.render();
        } else {
            //cleanup splash screen
            if (splashScreenDrawer != null) {
                splashScreenDrawer.dispose();
                splashScreenDrawer = null;
            }

            //TODO: enter game loop
        }
    }

    @Override
    public void pause() {
        //TODO: fire event
    }

    @Override
    public void resume() {
        //TODO: fire event
    }

    @Override
    public void dispose() {
        //TODO: fire event

        //TODO: interrupt game logic layer thread

        //TODO: shutdown subsystems
    }

}
