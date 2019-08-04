package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.applayer.init.Initializer;
import com.jukusoft.engine2d.core.config.Config;

@InitPriority(4)
public class DefaultConfigInitializer implements Initializer {

    private final Class<?> cls;

    public DefaultConfigInitializer(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public void init() throws Exception {
        Config.loadFromResource("defaultConfig.cfg", cls);
    }

}
