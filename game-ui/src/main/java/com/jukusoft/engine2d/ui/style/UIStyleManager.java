package com.jukusoft.engine2d.ui.style;

import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.ui.style.impl.UIStyleManagerImpl;

public interface UIStyleManager {

    public String getCurrentStylePath();

    public void setCurrentStylePath(String stylePath);

    public boolean isStyleAvailable(String stylePath);

    /**
     * load current style
     */
    public void load();

    /**
     * unload current style
     */
    public void unload();

    public UIStyle getCurrentStyle();

    public Array<UIStyle> listAllStyles();

    public void reload();

    public static UIStyleManager getInstance() {
        return UIStyleManagerImpl.getInstance();
    }

}
