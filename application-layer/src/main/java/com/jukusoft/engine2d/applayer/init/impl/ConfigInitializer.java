package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitBeforeSplashScreen;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.FileUtils;
import com.jukusoft.engine2d.core.utils.Utils;

import java.io.File;
import java.io.IOException;

@InitPriority(3)
@InitBeforeSplashScreen
public class ConfigInitializer implements Initializer {

    protected static final String CONFIG_TAG = "Config";

    @Override
    public void init() throws IOException {
        //create config directory, if not exists
        FileUtils.createWritableDirIfAbsent("config");

        //load all config files
        Utils.printSection("Configuration & Init");
        Log.i(CONFIG_TAG, "load ./config/game.cfg");

        try {
            Config.load(new File("./config/game.cfg"));
        } catch (IOException e) {
            Log.e(CONFIG_TAG, "IOException while loading config file ./config/game.cfg!", e);
            throw e;
        }
    }

}
