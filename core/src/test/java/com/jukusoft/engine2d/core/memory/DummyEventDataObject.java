package com.jukusoft.engine2d.core.memory;

import com.jukusoft.engine2d.core.events.EventData;

public class DummyEventDataObject extends EventData {

    @Override
    public int getEventType() {
        return 1;
    }

}
