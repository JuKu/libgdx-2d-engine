package com.jukusoft.engine2d.core.time;

public class GameSpeed {

    private static float speed = 1;

    private GameSpeed () {
        //
    }

    public static float getSpeed() {
        return speed;
    }

    public static void setSpeed(float speed) {
        GameSpeed.speed = speed;
    }

}
