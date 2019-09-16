package com.jukusoft.engine2d.applayer.init.impl;

import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.applayer.events.subsystem.AllSubSystemsInitializedEvent;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.applayer.utils.SubSystemInitializer;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.events.Events;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.memory.Pools;

import java.util.Objects;

@InitPriority(10)
public class UIThreadSubSystemsInitializer implements Initializer {

    private final ObjectArrayList<SubSystem> subSystemList;

    public UIThreadSubSystemsInitializer(ObjectArrayList<SubSystem> subSystemList) {
        Objects.requireNonNull(subSystemList);

        this.subSystemList = subSystemList;
    }

    @Override
    public void init() throws Exception {
        //initialize subsystems
        SubSystemInitializer.init(subSystemList);

        Log.i("SubSystems", "subsystems initialized successfully");
        Events.queueEvent(Pools.get(AllSubSystemsInitializedEvent.class));
    }

}
