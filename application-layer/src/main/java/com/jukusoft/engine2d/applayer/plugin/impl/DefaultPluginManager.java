package com.jukusoft.engine2d.applayer.plugin.impl;

import com.jukusoft.engine2d.applayer.plugin.PluginManager;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.Utils;
import com.jukusoft.engine2d.plugin.Pluggable;
import com.jukusoft.engine2d.plugin.PluginApi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
* singleton plugin manager which loads, starts and shutdown plugins
*/
public class DefaultPluginManager implements PluginManager {

    private static final String LOG_TAG = "PluginManager";

    //singleton instance
    private static DefaultPluginManager instance;

    private List<Pluggable> list;
    private List<Pluggable> startedPlugins = new ArrayList<>();

    private final PluginApi pluginApi;

    protected DefaultPluginManager(PluginApi pluginApi) {
        this.pluginApi = pluginApi;
    }

    @Override
    public void loadPlugins(File pluginDir) throws IOException {
        Objects.requireNonNull(pluginDir);
        Utils.printSection("Load Plugins");

        Log.i(LOG_TAG, "load all plugins...");
        this.list = PluginLoader.loadPlugins(pluginDir);

        for (Pluggable pluggable : this.list) {
            Log.i(LOG_TAG, "loaded plugin " + pluggable.getTitle(Locale.ENGLISH) + ", class: " + pluggable.getClass().getCanonicalName());
        }
    }

    @Override
    public void startAllPlugins(Game game) {
        Objects.requireNonNull(game);

        Utils.printSection("Start Plugins");

        for (Pluggable pluggable : this.list) {
            Log.i(LOG_TAG, "start plugin " + pluggable.getClass().getCanonicalName());
            pluggable.setPluginApi(pluginApi);
            pluggable.start(game);

            startedPlugins.add(pluggable);
        }
    }

    @Override
    public void shutdownAllPlugins(Game game) {
        Objects.requireNonNull(game);

        Utils.printSection("Shutdown Plugins");

        for (Pluggable pluggable : this.list) {
            Log.i(LOG_TAG, "shutdown plugin " + pluggable.getClass().getCanonicalName());
            pluggable.shutdown(game);

            startedPlugins.remove(pluggable);
        }
    }

    @Override
    public int countPlugins() {
        return list.size();
    }

    @Override
    public int countActivePlugins() {
        return startedPlugins.size();
    }

    /**
    * get singleton instance
     *
     * @return singleton instance
    */
    public static PluginManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("singleton instance wasn't created before");
        }

        return instance;
    }

    public static void createInstance(PluginApi pluginApi) {
        Objects.requireNonNull(pluginApi);

        if (instance != null) {
            throw new IllegalStateException("singleton instance was created before");
        }

        instance = new DefaultPluginManager(pluginApi);
    }

}
