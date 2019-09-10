package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitBeforeSplashScreen;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.Platform;
import com.jukusoft.engine2d.core.utils.PlatformUtils;
import com.jukusoft.engine2d.core.utils.Utils;

import java.util.Locale;

@InitPriority(4)
@InitBeforeSplashScreen
public class LogOSInitializer implements Initializer {

    private static final String LOG_TAG = "OS";

    @Override
    public void init() throws Exception {
        Utils.printSection("Platform");

        Log.i(LOG_TAG, "OS name: " + System.getProperty("os.name", "generic"));
        Log.i(LOG_TAG, "OS architecture: " + System.getProperty ("os.arch"));
    }

}
