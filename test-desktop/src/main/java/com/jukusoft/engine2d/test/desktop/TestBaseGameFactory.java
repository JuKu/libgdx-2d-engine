package com.jukusoft.engine2d.test.desktop;

import com.jukusoft.engine2d.applayer.BaseGame;
import com.jukusoft.engine2d.applayer.BaseGameFactory;
import com.jukusoft.engine2d.applayer.game.BasicGame;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.subsystem.EventProcessor;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;
import com.jukusoft.engine2d.core.utils.Threads;
import com.jukusoft.engine2d.input.subsystem.InputSubSystem;
import com.jukusoft.engine2d.plugin.PluginApi;
import com.jukusoft.engine2d.view.subsystem.ScreenSubSystem;

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

                    //add event processors for UI and logic events
                    manager.addSubSystem(new EventProcessor(Threads.UI_THREAD, 10), Threads.UI_THREAD);
                    manager.addSubSystem(new EventProcessor(Threads.LOGIC_THREAD, 10), Threads.LOGIC_THREAD);
                    manager.addSubSystem(new EventProcessor(Threads.NETWORK_THREAD, 10), Threads.NETWORK_THREAD);

                    //add game-view-layer
                    ScreenSubSystem screenSubSystem = new ScreenSubSystem();
                    screenSubSystem.getScreenManager().addScreen("test", new TestUIScreen());
                    screenSubSystem.getScreenManager().leaveAllAndEnter("test");
                    manager.addSubSystem(screenSubSystem, Threads.UI_THREAD);
                };
            }

            @Override
            protected Game createGame() {
                return new BasicGame();
            }
        };
    }

}
