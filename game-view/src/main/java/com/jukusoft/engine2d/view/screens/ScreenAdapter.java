package com.jukusoft.engine2d.view.screens;

public abstract class ScreenAdapter implements IScreen {

    @Override
    public void onStart(ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    public void onStop(ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    public void onResume(ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    public void onPause(ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    public void onResize(int oldWidth, int oldHeight, int width, int height) {
        //
    }

    @Override
    public void update(ScreenManager<IScreen> screenManager, float delta) {
        //
    }

    @Override
    public void draw(float delta) {
        //
    }

}
