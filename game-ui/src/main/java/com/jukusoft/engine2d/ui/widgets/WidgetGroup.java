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

    private float offsetX = 0;
    private float offsetY = 0;

    public void addWidget(Widget widget) {
        childWidgets.add(widget);
    }

    public void removeWidgets(Widget widget) {
        childWidgets.removeValue(widget, false);
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    @Override
    public void update(float delta, float offsetX, float offsetY) {
        for (Widget widget : childWidgets.items) {
            if (widget == null) {
                continue;
            }

            widget.update(delta, offsetX + this.getOffsetX(), offsetY + this.getOffsetY());
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

    @Override
    public boolean keyDown(int keycode) {
        for (Widget widget : childWidgets) {
            if (widget.keyDown(keycode))
                return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for (Widget widget : childWidgets) {
            if (widget.keyUp(keycode))
                return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        for (Widget widget : childWidgets) {
            if (widget.keyTyped(character))
                return true;
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (Widget widget : childWidgets) {
            if (widget.touchDown(screenX, screenY, pointer, button))
                return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (Widget widget : childWidgets) {
            if (widget.touchUp(screenX, screenY, pointer, button))
                return true;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (Widget widget : childWidgets) {
            if (widget.touchDragged(screenX, screenY, pointer))
                return true;
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for (Widget widget : childWidgets) {
            if (widget.mouseMoved(screenX, screenY))
                return true;
        }

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        for (Widget widget : childWidgets) {
            if (widget.scrolled(amount))
                return true;
        }

        return false;
    }

    @Override
    public <T extends Widget> T findWidgetbyId(String id, Class<T> cls) {
        if (this.getId().equals(id)) {
            return cls.cast(this);
        }

        //search inner widgets
        for (Widget widget : childWidgets) {
            T widget1 = widget.findWidgetbyId(id, cls);

            if (widget1 != null) {
                return widget1;
            }
        }

        return null;
    }
}
