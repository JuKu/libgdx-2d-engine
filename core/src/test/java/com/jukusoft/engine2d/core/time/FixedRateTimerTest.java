package com.jukusoft.engine2d.core.time;

import org.junit.Test;

import static org.junit.Assert.*;

public class FixedRateTimerTest {

    @Test
    public void testConstructor() {
        new FixedRateTimer();
    }

    @Test
    public void testRestart() {
        FixedRateTimer timer = new FixedRateTimer();
        assertFalse(timer.isStarted());
        assertEquals(0f, timer.getProgress(), 0.0001f);

        timer.start(1);
        assertTrue(timer.isStarted());
        assertEquals(0f, timer.getProgress(), 0.0001f);

        timer.update(0.5f);
        assertTrue(timer.isStarted());
        assertEquals(0.5f, timer.getProgress(), 0.0001f);

        //duration reached
        timer.update(0.5f);
        assertTrue(timer.isStarted());
        assertEquals(0f, timer.getProgress(), 0.0001f);

        timer.update(0.5f);
        assertTrue(timer.isStarted());
        assertEquals(0.5f, timer.getProgress(), 0.0001f);

        timer.update(1f);
        assertTrue(timer.isStarted());
        assertEquals(0.5f, timer.getProgress(), 0.0001f);
    }

}
