package com.jukusoft.engine2d.applayer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.jukusoft.engine2d.applayer.events.game.DisposeGameEvent;
import com.jukusoft.engine2d.applayer.events.game.PauseGameEvent;
import com.jukusoft.engine2d.applayer.events.game.ResumeGameEvent;
import com.jukusoft.engine2d.applayer.init.InitializerProcessor;
import com.jukusoft.engine2d.applayer.init.SplashScreenDrawer;
import com.jukusoft.engine2d.applayer.init.factory.InitializerProcessorFactory;
import com.jukusoft.engine2d.applayer.plugin.PluginManager;
import com.jukusoft.engine2d.applayer.plugin.impl.DefaultPluginManager;
import com.jukusoft.engine2d.applayer.window.WindowDimension;
import com.jukusoft.engine2d.basegame.events.window.ResizeWindowEvent;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.events.Events;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.memory.Pools;
import com.jukusoft.engine2d.core.shutdown.ErrorHandler;
import com.jukusoft.engine2d.core.task.TaskManager;
import com.jukusoft.engine2d.core.task.TaskManagers;
import com.jukusoft.engine2d.core.utils.Platform;
import com.jukusoft.engine2d.core.utils.Threads;
import com.jukusoft.engine2d.plugin.PluginApi;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseApp implements ApplicationListener {

    private final Class<?> gameClass;
    private InitializerProcessor initProcessor;
    private SplashScreenDrawer splashScreenDrawer;
    private boolean initialized = false;

    private WindowDimension windowDimension;
    private PluginManager pluginManager;

    //memory leak checker
    private int cachedMemoryLeakCheckerInterval;
    private long lastMemoryLeakCheck = 0;

    private Color clearColor = Color.BLACK;
    private long splashScreenStartTime;
    private long splashScreenMinTime;

    public BaseApp(Class<?> gameClass) {
        this.gameClass = gameClass;
    }

    @Override
    public final void create() {
        Thread.currentThread().setName("ui-thread");

        DefaultPluginManager.createInstance(createPluginApi());
        this.pluginManager = DefaultPluginManager.getInstance();

        //call abstract method to add initializers
        registerAdditionalInitializers();

        //initialize game engine
        this.initProcessor = InitializerProcessorFactory.create(this.gameClass, this.pluginManager);

        try {
            initProcessor.processBeforeSplashScreen();
        } catch (Exception e) {
            ErrorHandler.shutdownWithException(e);
        }

        this.splashScreenDrawer = new SplashScreenDrawer();
        this.windowDimension = new WindowDimension();
        this.windowDimension.update();

        this.splashScreenStartTime = System.currentTimeMillis();
        this.splashScreenMinTime = Config.getInt("SplashScreen", "minTime");

        this.cachedMemoryLeakCheckerInterval = Config.getInt("Pools", "memoryLeakCheckerInterval");
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

        long startTime = System.currentTimeMillis();

        if (!initialized) {
            //call initializers
            try {
                initProcessor.process();
            } catch (Exception e) {
                e.printStackTrace();
            }

            initialized = initProcessor.hasFinished() && (splashScreenStartTime + splashScreenMinTime < startTime);

            splashScreenDrawer.render();
        } else {
            //cleanup splash screen
            if (splashScreenDrawer != null) {
                splashScreenDrawer.dispose();
                splashScreenDrawer = null;

                Log.i("BaseApp", "Game Engine initialization finished");

                //initialize subsystems and so on
                onInitAfterSplashscreen();
            }

            //clear OpenGL buffer
            Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            //TODO: execute tasks
            TaskManager taskManager = TaskManagers.get(Threads.UI_THREAD);
            taskManager.run(10);

            //enter game loop
            onUpdate();

            //memory leak checker
            if (lastMemoryLeakCheck + cachedMemoryLeakCheckerInterval < startTime) {
                Pools.checkForMemoryLeaks();

                //update cached config value
                this.cachedMemoryLeakCheckerInterval = Config.getInt("Pools", "memoryLeakCheckerInterval");

                lastMemoryLeakCheck = startTime;
            }

            long endTime = System.currentTimeMillis();
            long timeDiff = endTime - startTime;
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
        //fire event
        Events.triggerEvent(Pools.get(DisposeGameEvent.class));

        onDispose();

        //interrupt game logic layer thread
        Threads.interruptAllThreads();

        //TODO: interrupt network thread

        //TODO: shutdown subsystems
    }

    protected abstract PluginApi createPluginApi();

    /**
     * method where other game layers can add initializers to game init
     *
     * @param initializers
     */
    protected abstract void addInitializers(List<Initializer> initializers);

    protected abstract void onInitAfterSplashscreen();

    protected abstract void onUpdate();

    protected abstract void onDispose();

    private void registerAdditionalInitializers() {
        List<Initializer> initializers = new ArrayList<>();
        this.addInitializers(initializers);

        for (Initializer initializer : initializers) {
            InitializerProcessorFactory.addGlobalInitializer(initializer);
        }
    }

}
