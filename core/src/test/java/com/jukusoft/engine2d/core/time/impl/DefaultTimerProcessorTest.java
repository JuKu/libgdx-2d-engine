package com.jukusoft.engine2d.core.time.impl;

import com.jukusoft.engine2d.core.time.Timer;
import com.jukusoft.engine2d.core.time.TimerProcessor;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class DefaultTimerProcessorTest {

    @Test
    public void testConstructor () {
        new DefaultTimerProcessor();
    }

    @Test (expected = NullPointerException.class)
    public void testAddNullTimer() {
        TimerProcessor timerProcessor = new DefaultTimerProcessor();
        timerProcessor.addTimer(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddStopedTimer() {
        TimerProcessor timerProcessor = new DefaultTimerProcessor();
        timerProcessor.addTimer(new Timer());
    }

    @Test
    public void testAddTimer() {
        TimerProcessor timerProcessor = new DefaultTimerProcessor();
        Timer timer = new Timer();
        timer.start(1f);
        timerProcessor.addTimer(timer);
    }

    @Test (expected = NullPointerException.class)
    public void testRemoveNullTimer() {
        TimerProcessor timerProcessor = new DefaultTimerProcessor();
        timerProcessor.removeTimer(null);
    }

    @Test
    public void testRemoveTimer() {
        TimerProcessor timerProcessor = new DefaultTimerProcessor();
        assertEquals(0, timerProcessor.countActiveTimers());

        Timer timer = new Timer();
        timer.start(1f);
        timerProcessor.addTimer(timer);
        assertEquals(1, timerProcessor.countActiveTimers());

        timerProcessor.removeTimer(timer);
        assertEquals(0, timerProcessor.countActiveTimers());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNegativeDeltaUpdate() {
        TimerProcessor timerProcessor = new DefaultTimerProcessor();

        //update empty timer list
        timerProcessor.update(-1f);
    }

    @Test
    public void testUpdate() {
        TimerProcessor timerProcessor = new DefaultTimerProcessor();

        //update empty timer list
        timerProcessor.update(1f);

        Timer timer = new Timer();
        timer.start(1f);
        timerProcessor.addTimer(timer);
        assertEquals(1, timerProcessor.countActiveTimers());

        //timer duration was not reached
        timer.update(0.5f);
        assertEquals(1, timerProcessor.countActiveTimers());

        //timer duration was reached, so remove the timer from list automatically
        timer.update(0.5f);
        assertEquals(0, timerProcessor.countActiveTimers());
    }

    @Test
    public void testCallListener() {
        AtomicBoolean b = new AtomicBoolean(false);

        TimerProcessor timerProcessor = new DefaultTimerProcessor();

        Timer timer = new Timer();
        timer.setListener(() -> b.set(true));
        timer.start(1f);
        timerProcessor.addTimer(timer);
        assertFalse(b.get());

        timerProcessor.update(0.5f);
        assertFalse(b.get());

        timerProcessor.update(0.5f);
        assertTrue(b.get());
    }

}
