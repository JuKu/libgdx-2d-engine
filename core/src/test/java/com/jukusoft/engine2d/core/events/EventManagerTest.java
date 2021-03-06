package com.jukusoft.engine2d.core.events;

import com.jukusoft.engine2d.core.memory.DummyEventDataObject;
import com.jukusoft.engine2d.core.memory.DummyOtherEventDataObject;
import com.jukusoft.engine2d.core.utils.Threads;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class EventManagerTest {

    @BeforeClass
    public static void beforeClass() {
        Threads.setThreadCount(1);
    }

    @Test
    public void testConstructor() {
        EventManager.instance = null;

        new EventManager("test", false);
        assertNull(EventManager.instance);
        assertNull(EventManager.getInstance());
    }

    @Test
    public void testSetGlobalConstructor() {
        EventManager.instance = null;

        new EventManager("test", true);
        assertNotNull(EventManager.instance);
        assertNotNull(EventManager.getInstance());

        //reset event manager
        EventManager.instance = null;
    }

    @Test
    public void testGetInstance() {
        EventManager manager = new EventManager("test", true);

        EventManager manager1 = EventManager.getInstance();
        EventManager manager2 = EventManager.getInstance();
        assertEquals(manager, manager1);
        assertEquals(manager1, manager2);

        //reset event manager
        EventManager.instance = null;
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullListener() {
        EventManager manager = new EventManager("test", false);
        manager.addListener(1, null);
    }

    @Test
    public void testAddListener() {
        EventManager manager = new EventManager("test", false);
        manager.addListener(1, eventData -> {
            //don't do anything here
        });
    }

    @Test
    public void testAddSameListener() {
        EventManager manager = new EventManager("test", false);
        manager.addListener(1, eventData -> {
            //don't do anything here
        });
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveNullListener() {
        EventManager manager = new EventManager("test", false);
        manager.removeListener(1, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveNotExistentListener() {
        EventManager manager = new EventManager("test", false);

        EventListener listener = (eventData -> {
            //don't do anything here
        });

        manager.addListener(2, listener);
        manager.addListener(2, listener);
    }

    @Test
    public void testAddAndRemoveListener() {
        EventManager manager = new EventManager("test", false);

        //there should not listener array exists yet
        assertNull(manager.listenerMap.get(2));

        //register listener
        EventListener listener = (eventData -> {
            //don't do anything here
        });
        manager.addListener(2, listener);

        //check, if listener is registered correctly
        assertNotNull(manager.listenerMap.get(2));
        assertEquals(1, manager.listenerMap.get(2).size);
        assertEquals(listener, manager.listenerMap.get(2).get(0));

        //remove other listener typeID
        manager.removeListener(3, listener);

        //check again, if listener is registered correctly
        assertNotNull(manager.listenerMap.get(2));
        assertEquals(1, manager.listenerMap.get(2).size);
        assertEquals(listener, manager.listenerMap.get(2).get(0));

        //remove other listener typeID
        manager.removeListener(2, listener);

        //check, if listener was removed
        assertNotNull(manager.listenerMap.get(2));
        assertEquals(0, manager.listenerMap.get(2).size);
    }

    @Test
    public void testUpdateEmptyQueue() {
        EventManager manager = new EventManager("test", false);
        manager.update();
    }

    @Test
    public void testUpdateEmptyQueue1() {
        EventManager manager = new EventManager("test", false);
        manager.update(10);
    }

    @Test
    public void testQueueEvent() {
        EventManager manager = new EventManager("test", false);
        assertEquals(0, manager.activeQueue);

        //check, if queues are empty
        assertNull(manager.eventQueue[0].poll());
        assertNull(manager.eventQueue[1].poll());

        //queue event and check, if event exists (only) in active queue
        manager.queueEvent(new DummyEventDataObject());
        assertNotNull(manager.eventQueue[0].poll());
        assertNull(manager.eventQueue[1].poll());

        //try to poll again
        assertNull(manager.eventQueue[0].poll());

        //add event to queue again, because poll() removes this event
        manager.queueEvent(new DummyEventDataObject());

        //process events
        manager.update();

        //check, if all queues are empty, this means event was processed
        assertNull(manager.eventQueue[0].poll());
        assertNull(manager.eventQueue[1].poll());
    }

    @Test
    public void testQueueMultipleEvent() {
        EventManager manager = new EventManager("test", false);

        //add events to queue
        manager.queueEvent(new DummyEventDataObject());
        manager.queueEvent(new DummyEventDataObject());
        manager.queueEvent(new DummyEventDataObject());


        //process events
        manager.update();

        //check, if all queues are empty, this means events was processed
        assertNull(manager.eventQueue[0].poll());
        assertNull(manager.eventQueue[1].poll());
    }

    @Test(expected = IllegalStateException.class)
    public void testTriggerUnsupportedEvent() {
        EventManager manager = new EventManager("test", false);
        manager.triggerEvent(new DummyEventDataObject());
    }

    @Test
    public void testTriggerEvent() {
        EventManager manager = new EventManager("test", false);

        AtomicInteger count = new AtomicInteger(0);

        EventListener listener = new EventListener() {
            @Override
            public void handleEvent(EventData eventData) {
                count.incrementAndGet();
            }
        };

        //add listener to queue
        manager.addListener(2, listener);

        manager.triggerEvent(new DummyOtherEventDataObject());

        //check, if event listener was called
        assertEquals(1, count.get());
    }

    @Test(expected = IllegalStateException.class)
    public void testTriggerEvent1() {
        EventManager manager = new EventManager("test", false);

        AtomicInteger count = new AtomicInteger(0);

        EventListener listener = new EventListener() {
            @Override
            public void handleEvent(EventData eventData) {
                count.incrementAndGet();
            }
        };

        //add listener to queue
        manager.addListener(2, listener);

        DummyOtherEventDataObject event = new DummyOtherEventDataObject();
        event.retain();
        assertEquals(2, event.getRefCount());

        //refCount > 1 is not allowed, so this will cause an IllegalStateException
        manager.triggerEvent(event);

        //check, if event listener was called
        assertEquals(1, count.get());
        assertEquals(1, event.getRefCount());
    }

    @Test
    public void testUpdate() {
        EventManager manager = new EventManager("test", false);

        AtomicBoolean b = new AtomicBoolean(false);
        AtomicInteger count = new AtomicInteger(0);

        EventListener listener = new EventListener() {
            @Override
            public void handleEvent(EventData eventData) {
                b.set(true);
                count.incrementAndGet();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //add listener to queue
        manager.addListener(1, listener);

        //queue event twice, listener requires 100ms to handle this event
        DummyEventDataObject event = new DummyEventDataObject();
        assertEquals(1, event.getRefCount());
        manager.queueEvent(event);
        manager.queueEvent(new DummyEventDataObject());
        manager.queueEvent(new DummyOtherEventDataObject());

        //process events
        manager.update();

        //check, if listener was executed
        assertEquals(true, b.get());

        //check, if all events was processed and only 2 listeners was called (2 events with typeID 1 and 1 event with typeID 2)
        assertEquals(2, count.get());

        //check reference count of event (1, because Pools.free() retains event again)
        assertEquals(1, event.getRefCount());

        //check, if all queues are empty, this means all events was processed
        assertNull(manager.eventQueue[0].poll());
        assertNull(manager.eventQueue[1].poll());
    }

    @Test
    public void testUpdateWithMillis() {
        EventManager manager = new EventManager("test", false);

        AtomicBoolean b = new AtomicBoolean(false);
        AtomicInteger count = new AtomicInteger(0);

        EventListener listener = new EventListener() {
            @Override
            public void handleEvent(EventData eventData) {
                b.set(true);
                count.incrementAndGet();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //add listener to queue
        manager.addListener(1, listener);

        //queue event twice, listener requires 100ms to handle this event
        manager.queueEvent(new DummyEventDataObject());
        manager.queueEvent(new DummyEventDataObject());

        //process events
        manager.update(10);

        //check, if listener was executed
        assertEquals(true, b.get());

        //check, if only one event was processed
        assertEquals(1, count.get());

        //check, if queue is empty, but other active queue contains new event
        assertNull(manager.eventQueue[0].poll());
        assertNotNull(manager.eventQueue[1].poll());
    }

}
