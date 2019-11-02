package com.jukusoft.engine2d.basegame.events.input;

import com.jukusoft.engine2d.basegame.events.BaseEvents;
import com.jukusoft.engine2d.core.events.EventData;

/**
 * event which is fired from input system or network system to move player's position
 */
public class MoveEvent extends EventData {

    //move direction (vector 2D)
    public float x = 0;
    public float y = 0;

    //movement speed between 0 and 1 (e.q. used from controller input)
    public float speed = 0;

    @Override
    public int getEventType() {
        return BaseEvents.MOVE_PLAYER;
    }

}
