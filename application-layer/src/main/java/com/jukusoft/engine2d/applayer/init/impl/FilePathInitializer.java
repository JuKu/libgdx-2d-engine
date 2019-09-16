package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.FilePath;

import java.io.File;
import java.io.FileNotFoundException;

@InitPriority(8)
public class FilePathInitializer implements Initializer {

    protected static final String SECTION_PATHS = "Paths";
    protected static final String LOG_TAG = "Paths";

    @Override
    public void init() throws Exception {
        String dataDir = FilePath.parse(Config.get(SECTION_PATHS, "dataDir"));

        //check, if data directory exists
        if (!new File(dataDir).exists()) {
            Log.e(LOG_TAG, "data directory '" + dataDir + "' doesn't exists!");
            throw new FileNotFoundException("data directory '" + dataDir + "' doesn't exists!");
        }

        FilePath.setDataDir(dataDir);

        String configDir = FilePath.parse(Config.get(SECTION_PATHS, "configDir"));
        String tempDir = FilePath.parse(Config.get(SECTION_PATHS, "tempDir"));

        //check, if config directories exists
        String[] dirs = configDir.split(";");

        for (String dir : dirs) {
            dir = FilePath.parse(dir);

            if (!new File(dir).exists()) {
                Log.e(LOG_TAG, "config directory '" + dir + "' doesn't exists!");

                Log.i(LOG_TAG, "Create config directory now: " + dir);
                new File(dir).mkdirs();
            }
        }

        //check, if temp directory exists, else create temp directory
        if (!new File(tempDir).exists()) {
            Log.i(LOG_TAG, "create new temp directory: " + tempDir);
            new File(tempDir).mkdirs();
        }

        Log.d(LOG_TAG, "config directory: " + configDir);
        Log.d(LOG_TAG, "temp directory: " + tempDir);

        FilePath.setConfigDirs(configDir);
        FilePath.setTempDir(tempDir);
    }

}
