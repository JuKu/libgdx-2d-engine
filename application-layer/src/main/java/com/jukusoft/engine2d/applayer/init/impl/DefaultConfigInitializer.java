package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitBeforeSplashScreen;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;

@InitPriority(3)
@InitBeforeSplashScreen
public class DefaultConfigInitializer implements Initializer {

    private static final String LOG_TAG = "DefaultConfigInitializer";

    private final Class<?> cls;

    public DefaultConfigInitializer(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public void init() throws Exception {
        //logger is not initialized here
        //Log.i(LOG_TAG, "load default config from classpath: config/defaultConfig.cfg");
        
        Config.loadFromResource("config/defaultConfig.cfg", cls);
    }

}
