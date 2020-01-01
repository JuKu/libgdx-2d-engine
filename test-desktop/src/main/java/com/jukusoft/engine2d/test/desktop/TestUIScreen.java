package com.jukusoft.engine2d.test.desktop;

import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.ui.UIDrawer;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenAdapter;
import com.jukusoft.engine2d.view.screens.ScreenManager;

public class TestUIScreen extends ScreenAdapter {

    private UIDrawer uiDrawer;

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        super.onResume(screenManager);

        //InputManager.getInstance().addFirst(uiDrawer);
    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        super.onPause(screenManager);
    }
}
