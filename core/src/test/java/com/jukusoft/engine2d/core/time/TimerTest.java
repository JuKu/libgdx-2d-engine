package com.jukusoft.engine2d.core.time;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class TimerTest {

    @Test
    public void testConstructor() {
        new Timer();
    }

    @Test
    public void testStartAndStop() {
        Timer timer = new Timer();
        assertFalse(timer.isStarted());

        timer.update(0);
        assertFalse(timer.isStarted());

        timer.start(1);
        assertTrue(timer.isStarted());

        timer.update(0);
        assertTrue(timer.isStarted());

        timer.update(1);
        assertFalse(timer.isStarted());

        timer.start(1);
        assertTrue(timer.isStarted());
    }

    @Test
    public void testStartAndStop1() {
        Timer timer = new Timer();
        assertFalse(timer.isStarted());

        timer.start(1);
        assertTrue(timer.isStarted());

        timer.stop();
        assertFalse(timer.isStarted());
    }

    @Test
    public void testCallListener () {
        AtomicBoolean b = new AtomicBoolean(false);

        Timer timer = new Timer();
        timer.setListener(() -> b.set(true));

        timer.start(1);
        timer.update(0);

        //timer should not be called here
        assertFalse(b.get());

        timer.update(1);

        //timer should be called here
        assertTrue(b.get());
    }

}
