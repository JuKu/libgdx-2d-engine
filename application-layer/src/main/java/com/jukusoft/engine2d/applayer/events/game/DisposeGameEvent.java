package com.jukusoft.engine2d.applayer.events.game;

import com.jukusoft.engine2d.applayer.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventData;

public class DisposeGameEvent extends EventData {

    @Override
    public int getEventType() {
        return BaseEvents.DISPOSE_GAME;
    }

}
