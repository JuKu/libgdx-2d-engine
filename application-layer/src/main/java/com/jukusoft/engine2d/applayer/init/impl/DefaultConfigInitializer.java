package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitBeforeSplashScreen;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.config.Config;

@InitPriority(3)
@InitBeforeSplashScreen
public class DefaultConfigInitializer implements Initializer {

    private final Class<?> cls;

    public DefaultConfigInitializer(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public void init() throws Exception {
        Config.loadFromResource("config/defaultConfig.cfg", cls);
    }

}
