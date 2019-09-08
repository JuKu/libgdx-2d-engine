package com.jukusoft.engine2d.applayer.plugin;

import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.basegame.service.Service;

import java.io.File;
import java.io.IOException;

public interface PluginManager extends Service {

    public void loadPlugins(File pluginDir) throws IOException;

    public void startAllPlugins(Game game);

    public void shutdownAllPlugins(Game game);

}
