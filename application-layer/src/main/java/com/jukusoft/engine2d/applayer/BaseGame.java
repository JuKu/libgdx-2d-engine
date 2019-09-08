package com.jukusoft.engine2d.applayer;

import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;

public abstract class BaseGame extends BaseApp {

    public BaseGame(Class<?> gameClass) {
        super(gameClass);
    }

    protected abstract void addSubSystems(SubSystemManager manager);

    protected abstract Game createGame();

}
