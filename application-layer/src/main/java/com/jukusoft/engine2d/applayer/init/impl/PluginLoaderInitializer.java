package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.applayer.plugin.PluginManager;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;

import java.io.File;

@InitPriority(100)
public class PluginLoaderInitializer implements Initializer {

    private static final String LOG_TAG = "PluginLoaderInit";
    private static final String PLUGIN_SECTION = "Plugins";

    private final PluginManager pluginManager;

    public PluginLoaderInitializer(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @Override
    public void init() throws Exception {
        if (!Config.getBool(PLUGIN_SECTION, "enabled")) {
            Log.i(LOG_TAG, "plugins are disabled, so we don't load them here");
            return;
        }

        //call PluginLoader and load plugins
        File pluginDir = new File(Config.get(PLUGIN_SECTION, "pluginDir"));

        if (!pluginDir.exists() && Config.getBool(PLUGIN_SECTION, "autoCreatePluginDir")) {
            pluginDir.mkdirs();
        }

        pluginManager.loadPlugins(pluginDir);
        Log.i(LOG_TAG, pluginManager.countPlugins() + " plugins loaded");
    }

}
