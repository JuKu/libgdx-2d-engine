package com.jukusoft.engine2d.applayer.events.subsystem;

import com.jukusoft.engine2d.basegame.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventData;

public class AllSubSystemsInitializedEvent extends EventData {

    @Override
    public int getEventType() {
        return BaseEvents.ALL_SUBSYSTEMS_READY;
    }

}
