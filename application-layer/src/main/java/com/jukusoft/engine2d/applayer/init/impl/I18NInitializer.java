package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.basegame.i18n.I18NHelper;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.Utils;
import com.jukusoft.i18n.I;
import com.jukusoft.i18n.logger.LogUtils;

import java.io.File;
import java.util.Locale;

@InitPriority(90)
public class I18NInitializer implements Initializer {

    private static final String LOG_TAG = I18NInitializer.class.getSimpleName();
    private static final String I18N_RUNTIME_LOG_TAG = "i18n";

    @Override
    public void init() throws Exception {
        Utils.printSection("i18n");

        File i18nFolder = I18NHelper.getI18NDir();
        Log.i(LOG_TAG, "i18n dir: " + i18nFolder.getAbsolutePath());

        if (!i18nFolder.exists()) {
            Log.i(LOG_TAG, "create i18n directory: " + i18nFolder.getAbsolutePath());
            i18nFolder.mkdirs();
        }

        LogUtils.setLogger((level, msg) -> {
            switch(level.getName().toUpperCase()) {
                case "SEVERE":
                    Log.e(I18N_RUNTIME_LOG_TAG, msg);
                    break;
                case "WARNING":
                    Log.w(I18N_RUNTIME_LOG_TAG, msg);
                case "INFO":
                    Log.i(I18N_RUNTIME_LOG_TAG, msg);
                    break;
                default:
                    Log.d(I18N_RUNTIME_LOG_TAG, msg);
                    break;
            }
        });
        I.init(i18nFolder, Locale.ENGLISH, "commons");

        String langToken = I18NHelper.getCurrentLanguageToken();
        Log.i(LOG_TAG, "select language: " + langToken);
        I.setLanguage(langToken);
    }

}
