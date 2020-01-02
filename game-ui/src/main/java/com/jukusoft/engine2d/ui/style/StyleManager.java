package com.jukusoft.engine2d.ui.style;

import com.badlogic.gdx.utils.Array;

public interface StyleManager {

    public String getCurrentStylePath();

    public void setCurrentStylePath(String stylePath);

    /**
     * load current style
     */
    public void load();

    public Style getCurrentStyle();

    public Array<Style> listAllStyles();

}
