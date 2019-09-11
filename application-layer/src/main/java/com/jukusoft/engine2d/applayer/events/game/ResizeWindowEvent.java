package com.jukusoft.engine2d.applayer.events.game;

import com.jukusoft.engine2d.applayer.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventData;

public class ResizeWindowEvent extends EventData {

    private int oldWidth;
    private int oldHeight;
    private int newWidth;
    private int newHeight;

    public void set(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    @Override
    public int getEventType() {
        return BaseEvents.RESIZE_WINDOW;
    }

    public int getOldWidth() {
        return oldWidth;
    }

    public int getOldHeight() {
        return oldHeight;
    }

    public int getNewWidth() {
        return newWidth;
    }

    public int getNewHeight() {
        return newHeight;
    }

}
