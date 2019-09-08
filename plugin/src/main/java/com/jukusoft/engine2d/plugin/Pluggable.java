package com.jukusoft.engine2d.plugin;

import com.jukusoft.engine2d.basegame.Game;

import java.util.Locale;

public interface Pluggable {

    /**
    * set api for plugins to interact with the game engine and the game
     *
     * @param pluginApi api to interact with the game engine and the game
    */
    public void setPluginApi(PluginApi pluginApi);

    /**
    * get name of plugin shown in UI
     *
     * @return name of plugin
    */
    public String getTitle(Locale locale);

    /**
    * get description of plugin shown in UI
     *
     * @return description of plugin
    */
    public String getDescription(Locale locale);

    /**
    * start plugin (set event listeners, register services and so on)
     *
     * @param game instance of game
    */
    public void start(Game game);

    /**
    * shutdown plugin, dispose assets and cleanup
     *
     * @param game instance of game
    */
    public void shutdown(Game game);

}
