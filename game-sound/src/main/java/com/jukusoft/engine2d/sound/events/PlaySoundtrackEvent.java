package com.jukusoft.engine2d.sound.events;

import com.jukusoft.engine2d.basegame.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventData;

/**
 * event which is fired to load and start playing a soundtrack in game engine
 */
public class PlaySoundtrackEvent extends EventData {

    public String filePath;
    public boolean loop;
    public float volume;

    @Override
    public int getEventType() {
        return BaseEvents.PLAY_SOUNDTRACK;
    }

    @Override
    public void reset() {
        super.reset();

        filePath = null;
        loop = false;
        volume = 1.0f;
    }
}
