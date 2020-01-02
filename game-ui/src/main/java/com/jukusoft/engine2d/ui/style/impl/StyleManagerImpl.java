package com.jukusoft.engine2d.ui.style.impl;

import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.ui.style.Style;
import com.jukusoft.engine2d.ui.style.StyleManager;

public class StyleManagerImpl implements StyleManager {

    private String currentStylePath = "styles/default.xml";

    @Override
    public String getCurrentStylePath() {
        return currentStylePath;
    }

    @Override
    public void setCurrentStylePath(String stylePath) {
        this.currentStylePath = stylePath;
    }

    @Override
    public void load() {
        //call asset manager to load ui style

        //TODO: add code here
    }

    @Override
    public Style getCurrentStyle() {
        return null;
    }

    @Override
    public Array<Style> listAllStyles() {
        return null;
    }

}
