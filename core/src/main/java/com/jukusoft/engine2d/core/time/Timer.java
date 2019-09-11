package com.jukusoft.engine2d.core.time;

public class Timer {

    private float elapsed;
    private float duration;
    private boolean started = false;
    private Runnable listener;

    public Timer() {
        //
    }

    public void update (float delta) {
        if (!started) {
            return;
        }

        this.elapsed += delta;

        if (this.elapsed >= this.duration) {
            //timer reached
            started = false;

            if (listener != null) {
                listener.run();
            }
        }
    }

    public void start(float duration) {
        this.elapsed = 0;
        this.duration = duration;
        this.started = true;
    }

    public void stop() {
        this.started = false;
    }

    public boolean isStarted() {
        return started;
    }

    public void setListener(Runnable listener) {
        this.listener = listener;
    }

}
