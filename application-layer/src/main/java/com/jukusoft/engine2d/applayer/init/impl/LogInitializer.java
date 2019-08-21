package com.jukusoft.engine2d.applayer.init.impl;

import com.badlogic.gdx.Gdx;
import com.jukusoft.engine2d.applayer.init.InitBeforeSplashScreen;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@InitPriority(1)
@InitBeforeSplashScreen
public class LogInitializer implements Initializer {

    @Override
    public void init() {
        //load logger config
        try {
            Config.load(Gdx.files.absolute("./config/logger.cfg").file());

            //initialize logger
            Log.init();
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Couldn't initialize config and logger!", e);
            System.exit(0);
        }
    }

}
