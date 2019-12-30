package com.jukusoft.engine2d.ui;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

public class UIScreen {

    private Array<Widget> childWidgets;

    public void addWidget(Widget widget) {
        childWidgets.add(widget);
    }

    public void removeWidgets(Widget widget) {
        childWidgets.removeValue(widget, false);
    }

    public Array<Widget> listWidgets() {
        return childWidgets;
    }

}
