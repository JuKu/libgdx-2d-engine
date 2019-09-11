package com.jukusoft.engine2d.core.time.impl;

import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.core.time.Timer;
import com.jukusoft.engine2d.core.time.TimerProcessor;

import java.util.Objects;

public class DefaultTimerProcessor implements TimerProcessor {

    private ObjectArrayList<Timer> timerList = new ObjectArrayList<>(10);

    @Override
    public void addTimer(Timer timer) {
        Objects.requireNonNull(timer);

        if (!timer.isStarted()) {
            throw new IllegalArgumentException("timer was not started");
        }

        timerList.add(timer);

        //if the timer finished, remove them from list (cleanup)
        timer.setInternalListener(() -> {
            if (!timer.isStarted()) {
                //remove timer from list
                removeTimer(timer);
            }
        });
    }

    @Override
    public void removeTimer(Timer timer) {
        Objects.requireNonNull(timer);
        timerList.removeAll(timer);
    }

    @Override
    public int countActiveTimers() {
        return timerList.size();
    }

    @Override
    public void update(float delta) {
        if (delta < 0) throw new IllegalArgumentException("delta has to be >= 0");

        for (int i = 0; i < timerList.size(); i++) {
            Timer timer = timerList.get(i);
            timer.update(delta);
        }
    }

}
