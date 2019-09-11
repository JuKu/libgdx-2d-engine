package com.jukusoft.engine2d.core.time;

public interface TimerProcessor {

    public void addTimer(Timer timer);

    public void removeTimer(Timer timer);

    public int countActiveTimers();

    public void update(float delta);

}
