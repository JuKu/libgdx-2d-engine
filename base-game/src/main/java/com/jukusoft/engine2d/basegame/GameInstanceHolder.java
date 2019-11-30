package com.jukusoft.engine2d.basegame;

public class GameInstanceHolder {

    private static Game instance;

    private GameInstanceHolder() {
        //
    }

    public static Game getInstance() {
        if (instance == null) {
            throw new IllegalStateException("game instance was not set before, call setInstance() first");
        }

        return instance;
    }

    public static void setInstance(Game instance) {
        GameInstanceHolder.instance = instance;
    }

}
