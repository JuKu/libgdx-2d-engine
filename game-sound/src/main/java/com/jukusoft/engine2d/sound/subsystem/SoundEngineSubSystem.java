package com.jukusoft.engine2d.sound.subsystem;

import com.badlogic.gdx.audio.Music;
import com.jukusoft.engine2d.basegame.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventListener;
import com.jukusoft.engine2d.core.events.Events;
import com.jukusoft.engine2d.core.memory.Pools;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.time.GameTime;
import com.jukusoft.engine2d.core.utils.Threads;
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

    @Override
    public void init() {
        this.assetManager = GameAssetManager.getInstance();

        Events.addListener(Threads.UI_THREAD, BaseEvents.PLAY_SOUNDTRACK, (EventListener<PlaySoundtrackEvent>) event -> {
            playBackgroundSoundtrack(event);
        });
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

        //unload soundtrack
        assetManager.unload(currentBackgroundSoundtrackPath);
    }

    private void playBackgroundSoundtrack(PlaySoundtrackEvent event) {
        if (backgroundSoundtrack != null) {
            if (backgroundSoundtrack.isPlaying()) {
                backgroundSoundtrack.stop();
            }
        }

        backgroundSoundtrack = null;

        //unload old soundtrack
        if (currentBackgroundSoundtrackPath != null) {
            assetManager.unload(currentBackgroundSoundtrackPath);
        }

        currentBackgroundSoundtrackPath = event.filePath;
        soundtrackVolume = event.volume;
        soundtrackLoop = event.loop;

        //load new soundtrack
        assetManager.load(event.filePath, Music.class);
        soundtrackLoading = true;
    }

}
