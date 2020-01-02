package com.jukusoft.engine2d.sound.controller;

import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.core.events.Events;
import com.jukusoft.engine2d.core.memory.Pools;
import com.jukusoft.engine2d.core.utils.StringUtils;
import com.jukusoft.engine2d.sound.events.PlayMultipleSoundtracksEvent;
import com.jukusoft.engine2d.sound.events.PlaySoundtrackEvent;

import java.util.Objects;

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
    public static void playBackgroundSoundtrack(String fileName, boolean loop, float volume) {
        StringUtils.checkNotNullAndNotEmpty(fileName, "soundtrack filename");

        //fire event to load an play the soundtrack
        PlaySoundtrackEvent event = Pools.get(PlaySoundtrackEvent.class);
        event.filePath = fileName;
        event.loop = loop;
        event.volume = volume;
        Events.queueEvent(event);
    }

    /**
     * play a soundtrack in background, e.q. for main menu or game soundtracks
     *
     * @param paths paths to background soundtracks
     */
    public static void playMultipleBackgroundSoundtrack(Array<String> paths, float volume) {
        Objects.requireNonNull(paths);

        PlayMultipleSoundtracksEvent event = Pools.get(PlayMultipleSoundtracksEvent.class);

        for (String path : paths) {
            PlaySoundtrackEvent event1 = Pools.get(PlaySoundtrackEvent.class);
            event1.filePath = path;
            event1.loop = false;
            event1.volume = volume;
            event.soundtrackEvents.add(event1);
        }

        //fire event to load an play the soundtracks
        Events.queueEvent(event);
    }

}
