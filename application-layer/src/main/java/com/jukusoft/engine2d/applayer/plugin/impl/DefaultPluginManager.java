package com.jukusoft.engine2d.applayer.plugin.impl;

import com.jukusoft.engine2d.applayer.plugin.PluginManager;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.plugin.Pluggable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DefaultPluginManager implements PluginManager {

    private static final String LOG_TAG = "PluginManager";

    private List<Pluggable> list;

    @Override
    public void loadPlugins(File pluginDir) throws IOException {
        Log.i(LOG_TAG, "load all plugins...");
        this.list = PluginLoader.loadPlugins(pluginDir);

        for (Pluggable pluggable : this.list) {
            Log.i(LOG_TAG, "loaded plugin " + pluggable.getTitle(Locale.ENGLISH) + ", class: " + pluggable.getClass().getCanonicalName());
        }
    }

    @Override
    public void startAllPlugins(Game game) {

    }

    @Override
    public void shutdownAllPlugins(Game game) {

    }

}
