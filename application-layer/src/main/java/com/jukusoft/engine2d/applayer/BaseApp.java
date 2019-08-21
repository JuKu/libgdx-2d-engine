package com.jukusoft.engine2d.applayer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.jukusoft.engine2d.applayer.events.game.PauseGameEvent;
import com.jukusoft.engine2d.applayer.events.game.ResizeWindowEvent;
import com.jukusoft.engine2d.applayer.events.game.ResumeGameEvent;
import com.jukusoft.engine2d.applayer.init.InitializerProcessor;
import com.jukusoft.engine2d.applayer.init.SplashScreenDrawer;
import com.jukusoft.engine2d.applayer.init.factory.InitializerProcessorFactory;
import com.jukusoft.engine2d.applayer.threads.BaseThreads;
import com.jukusoft.engine2d.applayer.window.WindowDimension;
import com.jukusoft.engine2d.core.events.Events;
import com.jukusoft.engine2d.core.memory.Pools;
import com.jukusoft.engine2d.core.shutdown.ErrorHandler;
import com.jukusoft.engine2d.core.task.TaskManager;
import com.jukusoft.engine2d.core.task.TaskManagers;
import com.jukusoft.engine2d.core.utils.Platform;

public abstract class BaseApp implements ApplicationListener {

    private final Class<?> gameClass;
    private InitializerProcessor initProcessor;
    private SplashScreenDrawer splashScreenDrawer;
    private boolean initialized = false;

    private WindowDimension windowDimension;

    public BaseApp(Class<?> gameClass) {
        this.gameClass = gameClass;
    }

    @Override
    public final void create() {
        Thread.currentThread().setName("ui-thread");

        //initialize game engine
        this.initProcessor = InitializerProcessorFactory.create(this.gameClass);

        try {
            initProcessor.processBeforeSplashScreen();
        } catch (Exception e) {
            ErrorHandler.shutdownWithException(e);
        }

        this.splashScreenDrawer = new SplashScreenDrawer();
        this.windowDimension = new WindowDimension();
        this.windowDimension.update();
    }

    @Override
    public void resize(int width, int height) {
        if (!initialized) {
            return;
        }

        //fire event
        ResizeWindowEvent event = Pools.get(ResizeWindowEvent.class);
        event.set(windowDimension.getWidth(), windowDimension.getHeight(), width, height);
        Events.queueEvent(event);

        windowDimension.update();
    }

    @Override
    public final void render() {
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

            //TODO: execute tasks
            TaskManager taskManager = TaskManagers.get(BaseThreads.UI_THREAD);
            taskManager.run(10);

            //TODO: enter game loop
        }
    }

    @Override
    public final void pause() {
        //fire event
        Events.queueEvent(Pools.get(PauseGameEvent.class));
    }

    @Override
    public final void resume() {
        //fire event
        Events.queueEvent(Pools.get(ResumeGameEvent.class));
    }

    @Override
    public final void dispose() {
        //TODO: fire event

        //TODO: interrupt game logic layer thread

        //TODO: shutdown subsystems
    }

}
