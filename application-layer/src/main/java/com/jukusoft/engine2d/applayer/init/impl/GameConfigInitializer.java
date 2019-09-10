package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.Utils;

import java.io.File;

@InitPriority(1)
public class GameConfigInitializer implements Initializer {

    protected static final String CONFIG_TAG = "Config";

    @Override
    public void init() throws Exception {
        Utils.printSection("Game Configuration");

        //load all config files
        //Utils.printSection("Game Configuration");
        Log.i(CONFIG_TAG, "load config dir: ./config/");

        Config.loadDir(new File("./config/"));
    }

}
