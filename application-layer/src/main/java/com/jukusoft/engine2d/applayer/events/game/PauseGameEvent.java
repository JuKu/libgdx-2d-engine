package com.jukusoft.engine2d.applayer.events.game;

import com.jukusoft.engine2d.basegame.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventData;

public class PauseGameEvent extends EventData {

    @Override
    public int getEventType() {
        return BaseEvents.PAUSE_GAME;
    }

}
