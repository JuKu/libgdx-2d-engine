package com.jukusoft.engine2d.basegame.events.input;

import com.jukusoft.engine2d.basegame.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventData;

/**
 * event which is fired from input system to take a screenshot
 */
public class TakeScreenshotEvent extends EventData {

    @Override
    public int getEventType() {
        return BaseEvents.TAKE_SCREENSHOT;
    }

}
