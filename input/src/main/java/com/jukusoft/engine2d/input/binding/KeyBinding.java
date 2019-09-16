package com.jukusoft.engine2d.input.binding;

public interface KeyBinding {

    public boolean keyDown(int keycode);

    public boolean keyUp(int keycode);

    public boolean keyTyped(char character);

}
