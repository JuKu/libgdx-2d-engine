package com.jukusoft.engine2d.basegame.loading;

import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.logger.Log;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class LoadingProcessorTest {

    @BeforeClass
    public static void beforeClass() {
        Log.initJUnitLogger(Log.LEVEL.DEBUG);
    }

    @AfterClass
    public static void afterClass() {
        Log.shutdown();
    }

    @Test
    public void testConstructor() {
        new LoadingProcessor();
    }

    @Test
    public void testProcessOnlyOne() throws Exception {
        AtomicInteger i = new AtomicInteger(0);

        LoadingProcessor processor = new LoadingProcessor();
        processor.addTask(game -> i.incrementAndGet());
        processor.addTask(game -> i.incrementAndGet());
        processor.addTask(game -> i.incrementAndGet());

        assertEquals(0, i.get());

        processor.process(Mockito.mock(Game.class));
        assertEquals(1, i.get());
    }

    @Test
    public void testIfTasksAreExecuted() throws Exception {
        AtomicInteger i = new AtomicInteger(0);

        LoadingProcessor processor = new LoadingProcessor();
        processor.addTask(game -> i.incrementAndGet());
        processor.addTask(game -> i.incrementAndGet());
        processor.addTask(game -> i.incrementAndGet());

        assertEquals(0, i.get());

        processor.processAll(Mockito.mock(Game.class));
        assertEquals(3, i.get());
    }

    @Test(timeout = 5000)
    public void testProcessWithMaxTime() throws Exception {
        AtomicInteger i = new AtomicInteger(0);

        LoadingProcessor processor = new LoadingProcessor();

        for (int k = 0; k < 3; k++) {
            processor.addTask(game -> {
                i.incrementAndGet();
                Thread.sleep(100);
            });
        }

        assertEquals(0, i.get());

        processor.process(Mockito.mock(Game.class), 50);
        assertEquals(1, i.get());

        processor.process(Mockito.mock(Game.class), 200);
        assertEquals(3, i.get());
    }

    @Test
    public void testProcessOrder() throws Exception {
        AtomicInteger i = new AtomicInteger(0);

        LoadingProcessor processor = new LoadingProcessor();
        processor.addTask(new TaskWithPriority1() {
            @Override
            public void load(Game game) throws Exception {
                i.set(1);
            }
        });
        processor.addTask(new TaskWithPriority3() {
            @Override
            public void load(Game game) throws Exception {
                i.set(3);
            }
        });
        processor.addTask(new TaskWithPriority2() {
            @Override
            public void load(Game game) throws Exception {
                i.set(2);
            }
        });

        assertEquals(0, i.get());

        //check execution order
        processor.process(Mockito.mock(Game.class));
        assertEquals(3, i.get());
        processor.process(Mockito.mock(Game.class));
        assertEquals(2, i.get());
        processor.process(Mockito.mock(Game.class));
        assertEquals(1, i.get());
    }

}
