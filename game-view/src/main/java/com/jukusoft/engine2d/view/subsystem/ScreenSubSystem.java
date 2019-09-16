package com.jukusoft.engine2d.view.subsystem;

import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenManager;
import com.jukusoft.engine2d.view.screens.impl.DefaultScreenManager;

public class ScreenSubSystem implements SubSystem {

    private ScreenManager<IScreen> screenManager;

    @Override
    public void init() {
        this.screenManager = new DefaultScreenManager();
    }

    @Override
    public void update() {
        this.screenManager.update();
        this.screenManager.draw();
    }

    @Override
    public void shutdown() {
        this.screenManager.dispose();
    }

    public ScreenManager<IScreen> getScreenManager() {
        return screenManager;
    }

}
