package com.jukusoft.engine2d.core.subsystem;

import com.jukusoft.engine2d.core.init.Initializer;

import java.util.List;

public interface SubSystemManager {

    /**
     * initialize subsystems
     *
     * //@param initializers list with initializers so subsystems can add initializers on startup, e.q. to load assets
     */
    /*@Deprecated
    public void init(List<Initializer> initializers);*/

    /**
     * add subsystem
     *
     * @param system subsystem to add
     * @param threadID threadID of the thread, where the system should be updated
     */
    public void addSubSystem(SubSystem system, int threadID);

    /**
     * remove subsystem
     *
     * @param system subsystem to remove
     */
    public void removeSubSystem(SubSystem system);

    /**
     * update all subsystems
     */
    public void run();

}
