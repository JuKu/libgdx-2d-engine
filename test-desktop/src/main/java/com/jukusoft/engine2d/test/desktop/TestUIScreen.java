package com.jukusoft.engine2d.test.desktop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.engine2d.basegame.mods.ModManager;
import com.jukusoft.engine2d.input.InputManager;
import com.jukusoft.engine2d.ui.UIDrawer;
import com.jukusoft.engine2d.ui.UIScreen;
import com.jukusoft.engine2d.ui.impl.UIDrawerImpl;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;
import com.jukusoft.engine2d.view.screens.IScreen;
import com.jukusoft.engine2d.view.screens.ScreenAdapter;
import com.jukusoft.engine2d.view.screens.ScreenManager;

import java.io.File;
import java.util.Objects;

public class TestUIScreen extends ScreenAdapter {

    private UIDrawer uiDrawer;

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        super.onResume(screenManager);

        //load maindata mods
        ModManager.getInstance().loadFromDir(new File("data/maindata"));

        this.uiDrawer = new UIDrawerImpl();
        this.uiDrawer.load("testscreen.xml");

        InputManager.getInstance().addFirst(uiDrawer);
    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        GameAssetManager.getInstance().unload("testscreen.xml");
        InputManager.getInstance().remove(uiDrawer);
    }

    @Override
    public void onResize(int oldWidth, int oldHeight, int width, int height) {
        uiDrawer.onResize(oldWidth, oldHeight, width, height);
    }

    @Override
    public void update(ScreenManager<IScreen> screenManager, float delta) {
        uiDrawer.update(delta);
    }

    @Override
    public void draw(float delta) {
        uiDrawer.draw();
    }
}
