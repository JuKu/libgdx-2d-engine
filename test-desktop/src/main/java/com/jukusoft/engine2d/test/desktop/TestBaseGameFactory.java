package com.jukusoft.engine2d.test.desktop;

import com.jukusoft.engine2d.applayer.BaseGame;
import com.jukusoft.engine2d.applayer.BaseGameFactory;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;

public class TestBaseGameFactory implements BaseGameFactory {

    @Override
    public BaseGame createGame() {
        return new BaseGame(TestBaseGameFactory.class) {
            @Override
            protected void addSubSystems(SubSystemManager manager) {
                //
            }
        };
    }

}
