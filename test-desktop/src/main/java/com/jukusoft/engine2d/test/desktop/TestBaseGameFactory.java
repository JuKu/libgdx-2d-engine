package com.jukusoft.engine2d.test.desktop;

import com.jukusoft.engine2d.applayer.BaseGame;
import com.jukusoft.engine2d.applayer.BaseGameFactory;
import com.jukusoft.engine2d.applayer.game.BasicGame;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;
import com.jukusoft.engine2d.core.utils.Threads;
import com.jukusoft.engine2d.input.subsystem.InputSubSystem;
import com.jukusoft.engine2d.plugin.PluginApi;

import java.util.function.Consumer;

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
            protected Consumer<SubSystemManager> addSubSystems() {
                return manager -> {
                    Log.i("TestBaseGameFactory", "add subsystems");
                    manager.addSubSystem(new InputSubSystem(), Threads.UI_THREAD);
                };
            }

            @Override
            protected Game createGame() {
                return new BasicGame();
            }
        };
    }

}
