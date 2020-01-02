package com.jukusoft.engine2d.view.subsystem;

import com.jukusoft.engine2d.basegame.events.BaseEvents;
import com.jukusoft.engine2d.basegame.events.window.ResizeWindowEvent;
import com.jukusoft.engine2d.core.events.EventListener;
import com.jukusoft.engine2d.core.events.Events;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.time.GameTime;
import com.jukusoft.engine2d.core.utils.Threads;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import com.jukusoft.engine2d.view.screens.impl.DefaultScreenManager;

public class ScreenSubSystem implements SubSystem {

    private ScreenManager<IScreen> screenManager = new DefaultScreenManager();
    private GameAssetManager assetManager;

    @Override
    public void init() {
        Events.addListener(Threads.UI_THREAD, BaseEvents.RESIZE_WINDOW, (EventListener<ResizeWindowEvent>) resizeEvent -> {
            screenManager.resize(resizeEvent.getOldWidth(), resizeEvent.getOldHeight(), resizeEvent.getNewWidth(), resizeEvent.getNewHeight());
        });

        assetManager = GameAssetManager.getInstance();
    }

    @Override
    public void update() {
        float delta = GameTime.getInstance().getDelta();

        //update asset manager
        //TODO: maybe call this method in another class instead
        assetManager.update();

        this.screenManager.update(delta);
        this.screenManager.draw(delta);
    }

    @Override
    public void shutdown() {
        this.screenManager.dispose();
    }

    public ScreenManager<IScreen> getScreenManager() {
        return screenManager;
    }

}
