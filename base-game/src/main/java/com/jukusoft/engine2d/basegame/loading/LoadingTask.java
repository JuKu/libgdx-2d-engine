package com.jukusoft.engine2d.basegame.loading;

import com.jukusoft.engine2d.basegame.Game;

@FunctionalInterface
public interface LoadingTask {

    /**
     * execute loading task
     */
    public void load() throws Exception;

}
