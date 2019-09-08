package com.jukusoft.engine2d.plugin;

import com.jukusoft.engine2d.basegame.Game;

public interface Pluggable {

    public void setPluginManager(PluginApi pluginApi);

    public void start(Game game);

    public void shutdown(Game game);

}
