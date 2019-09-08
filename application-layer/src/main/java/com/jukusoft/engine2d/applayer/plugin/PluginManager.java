package com.jukusoft.engine2d.applayer.plugin;

import com.jukusoft.engine2d.basegame.Game;

public interface PluginManager {

    public void loadPlugins(String path);

    public void startAllPlugins(Game game);

    public void shutdownAllPlugins(Game game);

}
