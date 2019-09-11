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

            onTimerReached();
        }
    }

    public void start(float duration) {
        if (duration <= 0) throw new IllegalArgumentException("duration has to be > 0");

        this.elapsed = 0;
        this.duration = duration;
        this.started = true;
    }

    public void stop() {
        this.started = false;
    }

    public float getElapsed() {
        return elapsed;
    }

    protected void setElapsed(float elapsed) {
        this.elapsed = elapsed;
    }

    public float getDuration() {
        return duration;
    }

    public float getProgress() {
        if (elapsed == 0) return 0;

        return min(elapsed, duration) / duration;
    }

    private float min(float a, float b) {
        return (a <= b) ? a : b;
    }

    public boolean isStarted() {
        return started;
    }

    public void setListener(Runnable listener) {
        this.listener = listener;
    }

    protected void onTimerReached() {
        //
    }

}
