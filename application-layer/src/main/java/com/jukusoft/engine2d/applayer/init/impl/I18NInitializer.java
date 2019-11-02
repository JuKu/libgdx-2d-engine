package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.FilePath;
import com.jukusoft.engine2d.core.utils.Utils;
import com.jukusoft.i18n.I;

import java.io.File;
import java.util.Locale;

@InitPriority(90)
public class I18NInitializer implements Initializer {

    private static final String SECTION_NAME = "I18N";
    private static final String LOG_TAG = I18NInitializer.class.getSimpleName();

    @Override
    public void init() throws Exception {
        Utils.printSection("i18n");

        String dir = Config.get(SECTION_NAME, "dir");
        File i18nFolder = new File(FilePath.parse(dir));
        Log.i(LOG_TAG, "i18n dir: " + i18nFolder.getAbsolutePath());

        if (!i18nFolder.exists()) {
            Log.i(LOG_TAG, "create i18n directory: " + i18nFolder.getAbsolutePath());
            i18nFolder.mkdirs();
        }

        I.init(i18nFolder, Locale.ENGLISH, "commons");

        String langToken = Config.get(SECTION_NAME, "token");
        Log.i(LOG_TAG, "select language: " + langToken);
        I.setLanguage(langToken);
    }

}
