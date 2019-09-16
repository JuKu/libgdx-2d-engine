package com.jukusoft.engine2d.test.desktop;

import com.jukusoft.engine2d.applayer.BaseGame;
import com.jukusoft.engine2d.applayer.BaseGameFactory;
import com.jukusoft.engine2d.applayer.game.BasicGame;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;
import com.jukusoft.engine2d.plugin.PluginApi;

public class TestBaseGameFactory implements BaseGameFactory {

    @Override
    public BaseGame createGame() {
        return new BaseGame(TestBaseGameFactory.class) {
            @Override
            protected PluginApi createPluginApi() {
                return new PluginApi() {
                    @Override
                    public Game getGame() {
                        return null;
                    }
                };
            }

            @Override
            protected void addSubSystems(SubSystemManager manager) {
                //
            }

            @Override
            protected Game createGame() {
                return new BasicGame();
            }
        };
    }

}
