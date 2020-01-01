package com.jukusoft.engine2d.test.desktop;

import com.jukusoft.engine2d.basegame.mods.ModManager;
import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.ui.UIDrawer;
import com.jukusoft.engine2d.ui.UIScreen;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenAdapter;
import com.jukusoft.engine2d.view.screens.ScreenManager;

import java.io.File;

public class TestUIScreen extends ScreenAdapter {

    private UIDrawer uiDrawer;

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        super.onResume(screenManager);

        //load maindata mods
        ModManager.getInstance().loadFromDir(new File("data/maindata"));

        //parse screen
        GameAssetManager.getInstance().load("testscreen.xml", UIScreen.class);
        GameAssetManager.getInstance().finishLoading("testscreen.xml");

        //InputManager.getInstance().addFirst(uiDrawer);
    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        super.onPause(screenManager);
    }
}
