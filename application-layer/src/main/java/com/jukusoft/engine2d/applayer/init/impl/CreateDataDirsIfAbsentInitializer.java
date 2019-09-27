package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.shutdown.ErrorHandler;
import com.jukusoft.engine2d.core.utils.FilePath;
import com.jukusoft.engine2d.core.utils.FileUtils;

import java.io.IOException;

@InitPriority(80)
public class CreateDataDirsIfAbsentInitializer implements Initializer {

    private static final String LOG_TAG = "Data Init";

    @Override
    public void init() throws Exception {
        Log.i(LOG_TAG, "create data directories if absent");

        try {
            checkDir("maindata");
            checkDir("dlc");
            checkDir("mods");
            checkDir("plugins");
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException while creating data dirs: ", e);
            ErrorHandler.shutdownWithException(e);
        }
    }

    private void checkDir(String dirName) throws IOException {
        Log.d(LOG_TAG, "check dir: " + dirName);
        FileUtils.createWritableDirIfAbsent(FilePath.getDataDir() + dirName);
        FileUtils.createFileIfAbsent(FilePath.getDataDir() + dirName + "/.keep", "test");
    }

}
