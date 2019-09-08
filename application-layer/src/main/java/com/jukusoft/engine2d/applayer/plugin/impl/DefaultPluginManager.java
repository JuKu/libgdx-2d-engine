package com.jukusoft.engine2d.applayer.plugin.impl;

import com.jukusoft.engine2d.applayer.plugin.PluginManager;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.plugin.Pluggable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DefaultPluginManager implements PluginManager {

    @Override
    public void loadPlugins(File pluginDir) throws IOException {
        List<Pluggable> list = PluginLoader.loadPlugins(pluginDir);
    }

    @Override
    public void startAllPlugins(Game game) {

    }

    @Override
    public void shutdownAllPlugins(Game game) {

    }

}
