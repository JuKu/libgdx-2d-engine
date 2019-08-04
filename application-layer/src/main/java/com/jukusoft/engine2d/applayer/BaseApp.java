package com.jukusoft.engine2d.applayer;

import com.badlogic.gdx.ApplicationListener;
import com.jukusoft.engine2d.applayer.init.InitializerProcessor;
import com.jukusoft.engine2d.applayer.init.factory.InitializerProcessorFactory;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.version.Version;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseApp implements ApplicationListener {

    private final Class<? extends BaseApp> gameClass;
    private InitializerProcessor initProcessor;
    private boolean initialized = false;

    public BaseApp(Class<? extends BaseApp> gameClass) {
        this.gameClass = gameClass;
    }

    @Override
    public void create() {
        //initialize game engine
        this.initProcessor = InitializerProcessorFactory.create(this.gameClass);
        try {
            initProcessor.processBeforeSplashScreen();
        } catch (Exception e) {
            e.printStackTrace();

            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception while initializing game: ", e);
            Log.e("BaseApp", "Exception while initializing game engine: ", e);

            Log.shutdown();

            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.exit(0);
        }
    }

    @Override
    public void resize(int width, int height) {
        if (!initialized) {
            return;
        }

        //TODO: add code here
    }

    @Override
    public void render() {
        if (!initialized) {
            //call initializers
            try {
                initProcessor.process();
            } catch (Exception e) {
                e.printStackTrace();
            }

            initialized = initProcessor.hasFinished();
        } else {
            //TODO: enter game loop
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
