package com.jukusoft.engine2d.sound.controller;

import com.jukusoft.engine2d.core.events.Events;
import com.jukusoft.engine2d.core.memory.Pools;
import com.jukusoft.engine2d.core.utils.StringUtils;
import com.jukusoft.engine2d.sound.events.PlaySoundtrackEvent;

/**
 * helper class to make usage of sound engine easier.
 * The methods of this class only raises events to inform the sound engine
 */
public class SoundEngineController {

    private SoundEngineController() {
        //
    }

    /**
     * play a soundtrack in background, e.q. for main menu or game soundtracks
     *
     * @param fileName fileName to search in asset packs
     */
    public static void playBackgroundSoundtrack(String fileName) {
        StringUtils.checkNotNullAndNotEmpty(fileName, "soundtrack filename");

        //fire event to load an play the soundtrack
        PlaySoundtrackEvent event = Pools.get(PlaySoundtrackEvent.class);
        event.filePath = fileName;
        Events.queueEvent(event);
    }

}
