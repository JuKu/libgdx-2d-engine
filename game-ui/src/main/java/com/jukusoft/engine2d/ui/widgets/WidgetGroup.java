package com.jukusoft.engine2d.ui.widgets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.ui.Widget;
import com.jukusoft.engine2d.ui.WidgetAdapter;

/**
 * a group of widgets, which can be contain multiple widgets.
 * If the widget group is not visible, all the widgets inside the widget group are also not visible.
 */
public class WidgetGroup extends WidgetAdapter {

    private Array<Widget> childWidgets;

    public void addWidget(Widget widget) {
        childWidgets.add(widget);
    }

    public void removeWidgets(Widget widget) {
        childWidgets.removeValue(widget, false);
    }


    @Override
    public void update(float delta, float offsetX, float offsetY) {
        for (Widget widget : childWidgets.items) {
            if (widget == null) {
                continue;
            }

            widget.update(delta, offsetX, offsetY);
        }
    }

    @Override
    public void draw(SpriteBatch batch, float offsetX, float offsetY) {
        for (Widget widget : childWidgets.items) {
            if (widget == null) {
                continue;
            }

            widget.draw(batch, offsetX, offsetY);
        }
    }
    
}
