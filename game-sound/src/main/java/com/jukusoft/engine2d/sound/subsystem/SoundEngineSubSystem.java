package com.jukusoft.engine2d.sound.subsystem;

import com.badlogic.gdx.audio.Music;
import com.jukusoft.engine2d.basegame.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventListener;
import com.jukusoft.engine2d.core.events.Events;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.memory.Pools;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.time.GameTime;
import com.jukusoft.engine2d.core.utils.Threads;
import com.jukusoft.engine2d.sound.events.PlayMultipleSoundtracksEvent;
import com.jukusoft.engine2d.sound.events.PlaySoundtrackEvent;
import com.jukusoft.engine2d.sound.events.SoundtrackFinishedEvent;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;

public class SoundEngineSubSystem implements SubSystem {

    private GameAssetManager assetManager;

    //background soundtrack
    private String currentBackgroundSoundtrackPath = null;
    private Music backgroundSoundtrack = null;

    private boolean soundtrackLoading = false;
    private float soundtrackVolume = 1.0f;
    private boolean soundtrackLoop = false;

    private boolean initialized = false;

    @Override
    public void init() {
        Log.d(SoundEngineSubSystem.class.getSimpleName(), "initialize sound engine");

        if (initialized) {
            throw new IllegalStateException("sound engine was already initialized");
        }

        this.assetManager = GameAssetManager.getInstance();

        Events.addListener(Threads.UI_THREAD, BaseEvents.PLAY_SOUNDTRACK, (EventListener<PlaySoundtrackEvent>) event -> {
            playBackgroundSoundtrack(event);
        });

        Events.addListener(Threads.UI_THREAD, BaseEvents.PLAY_MULTIPLE_SOUNDTRACKS, (EventListener<PlayMultipleSoundtracksEvent>) event -> {
            playMultipleBackgroundSoundtracks(event);
        });

        initialized = true;
    }

    @Override
    public void update() {
        float delta = GameTime.getInstance().getDelta();

        //check, if soundtrack is loading
        if (soundtrackLoading) {
            //check, if soundtrack loading has finished
            if (assetManager.isLoaded(currentBackgroundSoundtrackPath)) {
                backgroundSoundtrack = assetManager.get(currentBackgroundSoundtrackPath);
                backgroundSoundtrack.setVolume(soundtrackVolume);
                backgroundSoundtrack.setLooping(soundtrackLoop);
                backgroundSoundtrack.play();

                if (!soundtrackLoop) {
                    backgroundSoundtrack.setOnCompletionListener(music -> {
                        //throw event, that soundtrack has finished playing
                        SoundtrackFinishedEvent event = Pools.get(SoundtrackFinishedEvent.class);
                        Events.queueEvent(event);
                    });
                }

                soundtrackLoading = false;
            }
        }
    }

    @Override
    public void shutdown() {
        if (backgroundSoundtrack != null) {
            if (backgroundSoundtrack.isPlaying()) {
                backgroundSoundtrack.stop();
            }
        }

        //we do not need to unload assets here, because asset manager does this automatically itself
        //unload soundtrack
        //assetManager.unload(currentBackgroundSoundtrackPath);
    }

    private void playBackgroundSoundtrack(PlaySoundtrackEvent event) {
        Log.i(SoundEngineSubSystem.class.getSimpleName(), "load and start soundtrack: " + event.filePath);

        if (backgroundSoundtrack != null) {
            if (backgroundSoundtrack.isPlaying()) {
                Log.d(SoundEngineSubSystem.class.getSimpleName(), "stop old soundtrack: " + currentBackgroundSoundtrackPath);
                backgroundSoundtrack.stop();
            }
        }

        backgroundSoundtrack = null;

        //unload old soundtrack
        if (currentBackgroundSoundtrackPath != null) {
            Log.d(SoundEngineSubSystem.class.getSimpleName(), "unload old soundtrack: " + currentBackgroundSoundtrackPath);
            assetManager.unload(currentBackgroundSoundtrackPath);
        }

        currentBackgroundSoundtrackPath = event.filePath;
        soundtrackVolume = event.volume;
        soundtrackLoop = event.loop;

        //load new soundtrack
        Log.d(SoundEngineSubSystem.class.getSimpleName(), "load new soundtrack: " + currentBackgroundSoundtrackPath);
        assetManager.load(currentBackgroundSoundtrackPath, Music.class);
        soundtrackLoading = true;
    }

    private void playMultipleBackgroundSoundtracks(PlayMultipleSoundtracksEvent event) {
        //TODO: add code here

        throw new UnsupportedOperationException("method is not implemented yet");
    }

}
