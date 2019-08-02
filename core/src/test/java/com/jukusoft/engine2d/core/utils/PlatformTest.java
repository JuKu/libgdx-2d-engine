package com.jukusoft.engine2d.core.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlatformTest {

    @Test
    public void testConstructor() {
        new Platform();
    }

    @Test
    public void testAdd() {
        Platform.clearQueue();

        assertEquals(0, Platform.getQueueSize());

        Platform.runOnUIThread(() -> {
            //
        });

        assertEquals(1, Platform.getQueueSize());
    }

    @Test
    public void testExecute() {
        Platform.clearQueue();

        assertEquals(0, Platform.getQueueSize());

        Platform.runOnUIThread(() -> {
            //
        });

        assertEquals(1, Platform.getQueueSize());

        //execute queue
        Platform.executeQueue();

        assertEquals(0, Platform.getQueueSize());

        //execute again
        Platform.executeQueue();
    }

}
