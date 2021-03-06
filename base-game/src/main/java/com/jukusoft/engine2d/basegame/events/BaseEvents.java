package com.jukusoft.engine2d.basegame.events;

public class BaseEvents {

    //general game events
    public static final int INITIALIZED = 0;
    public static final int PAUSE_GAME = 1;
    public static final int RESUME_GAME = 2;
    public static final int DISPOSE_GAME = 3;

    //window events
    public static final int RESIZE_WINDOW = 4;

    public static final int ALL_SUBSYSTEMS_READY = 5;
    public static final int TAKE_SCREENSHOT = 6;

    //player / camera movement (e.q. used by controllers)
    public static final int MOVE_PLAYER = 7;

    //sound engine
    public static final int PLAY_SOUNDTRACK = 8;
    public static final int PLAY_MULTIPLE_SOUNDTRACKS = 9;
    public static final int SOUNDTRACK_FINISHED = 10;

}
