package com.jukusoft.engine2d.ui;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.ui.dto.Soundtrack;

import java.util.Objects;

public class UIScreen extends InputAdapter {

    private String id;
    private Array<Widget> childWidgets = new Array<>();

    private int posX;
    private int posY;
    private String width;
    private String height;

    private String background = null;
    private Array<String> styles = new Array<>();
    private Array<Soundtrack> soundtracks = new Array<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addWidget(Widget widget) {
        Objects.requireNonNull(widget);
        childWidgets.add(widget);
    }

    public void removeWidgets(Widget widget) {
        Objects.requireNonNull(widget);
        childWidgets.removeValue(widget, false);
    }

    public Array<Widget> listWidgets() {
        return childWidgets;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void addStyle(String fileName) {
        styles.add(fileName);
    }

    public Array<String> listStyles() {
        return styles;
    }

    public void addSoundtrack(Soundtrack soundtrack) {
        soundtracks.add(soundtrack);
    }

    public void removeSoundtrack(Soundtrack soundtrack) {
        soundtracks.removeValue(soundtrack, false);
    }

    public Array<Soundtrack> listSoundtracks() {
        return soundtracks;
    }

    /**
     * find widget by id
     *
     * @param id id of the widget
     * @param cls return class
     * @param <T> widget type
     *
     * @return widget with the specific id
     */
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

}
