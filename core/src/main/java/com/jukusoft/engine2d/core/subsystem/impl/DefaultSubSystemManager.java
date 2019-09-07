package com.jukusoft.engine2d.core.subsystem.impl;

import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;
import com.jukusoft.engine2d.core.utils.StringUtils;
import com.jukusoft.engine2d.core.utils.ThreadUtils;

import java.util.List;
import java.util.Objects;

/**
* default subsystem manager. Every thread has it's own subsystem manager
*/
public class DefaultSubSystemManager implements SubSystemManager {

    //list with subsystems
    protected ObjectArrayList<SubSystem> subSystems = new ObjectArrayList<>();

    /**
    * name of subsystem manager
    */
    private final String name;

    public DefaultSubSystemManager(String name) {
        StringUtils.checkNotNullAndNotEmpty(name, "name");
        this.name = name;
    }

    @Override
    public void init(List<Initializer> initializers) {
        Objects.requireNonNull(initializers);

        //check, if current thread is main thread
        ThreadUtils.checkIfMainThread();

        //initialize subsystems in main thread
        this.subSystems.iterator().forEachRemaining(system -> system.value.init(initializers));
    }

    @Override
    public void addSubSystem(SubSystem system, boolean useExtraThread) {
        this.subSystems.add(system);
        Log.i("SubSystems_" + getName(), "added subsystem " + system.getClass().getCanonicalName());
    }

    @Override
    public void removeSubSystem(SubSystem system) {
        this.subSystems.remove(this.subSystems.lastIndexOf(system));
        Log.i("SubSystems_" + getName(), "removed subsystem " + system.getClass().getCanonicalName());
    }

    @Override
    public void run() {
        //
    }

    public String getName() {
        return name;
    }

}
