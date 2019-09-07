package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.events.Events;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;

@InitPriority(5)
public class EventInitializer implements Initializer {

    @Override
    public void init() throws Exception {
        Log.i("Events", "initialize event managers");
        Events.init();
    }

}
