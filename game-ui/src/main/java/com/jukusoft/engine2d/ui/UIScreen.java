package com.jukusoft.engine2d.ui;

import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.ui.dto.Soundtrack;

import java.util.Objects;

public class UIScreen {

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

}
