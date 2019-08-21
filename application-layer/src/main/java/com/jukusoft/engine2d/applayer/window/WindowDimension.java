package com.jukusoft.engine2d.applayer.window;

import com.badlogic.gdx.Gdx;

public class WindowDimension {

    //old window dimension
    private int windowWidth = 0;
    private int windowHeight = 0;

    public WindowDimension() {
        //
    }

    public int getWidth() {
        return windowWidth;
    }

    public int getHeight() {
        return windowHeight;
    }

    public void set(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
    }

    public void update () {
        set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

}
