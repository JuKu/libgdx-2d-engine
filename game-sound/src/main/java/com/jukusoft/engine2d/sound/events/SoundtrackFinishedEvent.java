package com.jukusoft.engine2d.sound.events;

import com.jukusoft.engine2d.basegame.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventData;

/**
 * event which is fired from sound engine, if the soundtrack playing has finished
 */
public class SoundtrackFinishedEvent extends EventData {

    @Override
    public int getEventType() {
        return BaseEvents.SOUNDTRACK_FINISHED;
    }

}
