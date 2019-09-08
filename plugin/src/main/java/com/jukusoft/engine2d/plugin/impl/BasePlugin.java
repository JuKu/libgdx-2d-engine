package com.jukusoft.engine2d.plugin.impl;

import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.plugin.Pluggable;
import com.jukusoft.engine2d.plugin.PluginApi;

public abstract class BasePlugin implements Pluggable {

    protected PluginApi pluginApi;

    @Override
    public final void setPluginApi(PluginApi pluginApi) {
        this.pluginApi = pluginApi;
    }

    @Override
    public final void start(Game game) {
        onStart(game, pluginApi);
    }

    @Override
    public final void shutdown(Game game) {
        onShutdown(game, pluginApi);
    }

    protected PluginApi getPluginApi() {
        return pluginApi;
    }

    public abstract void onStart(Game game, PluginApi pluginApi);

    public abstract void onShutdown(Game game, PluginApi pluginApi);

}
