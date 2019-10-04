package com.jukusoft.engine2d.basegame.loading;

import com.jukusoft.engine2d.basegame.Game;

@FunctionalInterface
public interface LoadingTask {

    /**
     * execute loading task
     *
     * @param game instance of game
     * @return true, if loading task executed successfully
     */
    public boolean load(Game game) throws Exception;

}
