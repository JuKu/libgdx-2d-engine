package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitBeforeSplashScreen;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.basegame.mods.ModManager;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.FilePath;
import com.jukusoft.engine2d.core.utils.Utils;

import java.io.File;

@InitBeforeSplashScreen
@InitPriority(8)
public class ModManagerInitializer implements Initializer {

    private static final String LOG_TAG = "ModLoader";

    @Override
    public void init() throws Exception {
        Utils.printSection("Mod Loader");

        //load mods

        String modDirs = Config.get("Mods", "modDirs");
        String[] dirArray = modDirs.split(",");

        for (String dir : dirArray) {
            File modDir = new File(FilePath.parse(dir));
            Log.i(LOG_TAG, "load mods from directory: " + modDir.getAbsolutePath());
            ModManager.getInstance().loadFromDir(modDir);
        }
    }

}
