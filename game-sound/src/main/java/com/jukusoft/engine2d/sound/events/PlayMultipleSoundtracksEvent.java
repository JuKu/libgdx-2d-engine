package com.jukusoft.engine2d.sound.events;

import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.basegame.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventData;
import com.jukusoft.engine2d.core.memory.Pools;

public class PlayMultipleSoundtracksEvent extends EventData {

    public Array<PlaySoundtrackEvent> soundtrackEvents = new Array<>(10);

    //loop all soundtracks
    public boolean loop = false;//TODO: add support for multiple soundtrack looping

    @Override
    public int getEventType() {
        return BaseEvents.PLAY_MULTIPLE_SOUNDTRACKS;
    }

    @Override
    public void reset() {
        super.reset();

        for (PlaySoundtrackEvent event : soundtrackEvents) {
            Pools.free(event);
        }

        soundtrackEvents.clear();
        loop = false;
    }
}
