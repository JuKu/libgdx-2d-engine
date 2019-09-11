package com.jukusoft.engine2d.core.time;

public class FixedRateTimer extends Timer {

    @Override
    protected void onTimerReached() {
        //save elapsed time
        float elapsed = getElapsed();

        //start timer again
        start(getDuration());

        setElapsed(elapsed % getDuration());
    }
}
