package com.jukusoft.engine2d.core.memory;

import com.jukusoft.engine2d.core.events.EventData;

public class DummyOtherEventDataObject extends EventData {

    @Override
    public int getEventType() {
        return 2;
    }

    @Override
    public boolean allowTrigger() {
        return true;
    }
}
