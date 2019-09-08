package com.jukusoft.engine2d.applayer.plugin;

import com.jukusoft.engine2d.basegame.Game;

import java.io.File;
import java.io.IOException;

public interface PluginManager {

    public void loadPlugins(File pluginDir) throws IOException;

    public void startAllPlugins(Game game);

    public void shutdownAllPlugins(Game game);

}
