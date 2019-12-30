package com.jukusoft.engine2d.ui.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.engine2d.ui.UIDrawer;
import com.jukusoft.engine2d.ui.Widget;

import java.net.URI;

public class UIDrawerImpl implements UIDrawer {

    private boolean debugMode = false;

    @Override
    public void load(URI uri) {

    }

    @Override
    public void addWidget(Widget widget) {

    }

    @Override
    public void removeWidget(Widget widget) {

    }

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

}
