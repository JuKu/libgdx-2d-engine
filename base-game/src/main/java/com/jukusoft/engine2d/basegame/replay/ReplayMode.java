package com.jukusoft.engine2d.basegame.replay;

import com.jukusoft.engine2d.core.config.Config;

public class ReplayMode {

    public enum Mode {
        DISABLED,
        ENABLED,
        REPLAY_PLAY
    }

    private ReplayMode() {
        //
    }

    /**
     * check, if the replay mode is enabled in config
     *
     * @return true, if the replay mode is enabled in config (this does not means it's also running)
     */
    public static boolean isEnabled() {
        return Config.getBool("Replay", "enabled");
    }

    public static boolean isRunning() {
        //TODO: add code here

        return false;
    }

}
