package com.jukusoft.engine2d.core.subsystem;

import com.jukusoft.engine2d.core.events.Events;
import com.jukusoft.engine2d.core.init.Initializer;

import java.util.List;

public class EventProcessor implements SubSystem {

    protected final int threadID;
    protected final int maxMillis;

    /**
     * default constructor
     *
     * @param threadID  thread ID
     * @param maxMillis maximum time in milliseconds, how long event proccessing can take
     */
    public EventProcessor(int threadID, int maxMillis) {
        this.threadID = threadID;
        this.maxMillis = maxMillis;
    }

    @Override
    public void init(List<Initializer> initializers) {
        //
    }

    @Override
    public void update() {
        Events.update(this.threadID, this.maxMillis);
    }

    @Override
    public void shutdown() {
        //
    }

}
