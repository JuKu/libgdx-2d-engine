package com.jukusoft.engine2d.ui.impl;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.engine2d.ui.UIDrawer;
import com.jukusoft.engine2d.ui.UIScreen;
import com.jukusoft.engine2d.ui.Widget;

import java.net.URI;

public class UIDrawerImpl extends InputAdapter implements UIDrawer {

    private UIScreen screen;
    private boolean debugMode = false;

    public UIDrawerImpl(UIScreen screen) {
        this.screen = screen;
    }

    @Override
    public void load(URI uri) {

    }

    /*@Override
    public void addWidget(Widget widget) {

    }*/

    /*@Override
    public void removeWidget(Widget widget) {

    }*/

    @Override
    public void reload() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public <T extends Widget> T findWidgetbyId(String id, Class<T> cls) {
        return null;
    }

    @Override
    public void setDebug(boolean debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public boolean isDebug() {
        return this.debugMode;
    }

    @Override
    public void addFeatureTag(String featureTag) {

    }

    @Override
    public void removeFeatureTag(String featureTag) {

    }

    @Override
    public boolean hasFeatureTag(String featureTag) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return screen.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return screen.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return screen.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return screen.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return screen.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return screen.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return screen.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return screen.scrolled(amount);
    }
}
