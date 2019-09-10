package com.jukusoft.engine2d.core.subsystem;

import com.jukusoft.engine2d.core.init.Initializer;

import java.util.List;

public interface SubSystemManager {

    /**
    * initialize subsystems
     *
     * @param initializers list with initializers so subsystems can add initializers on startup, e.q. to load assets
    */
    @Deprecated
    public void init(List<Initializer> initializers);

    public void addSubSystem(SubSystem system, boolean useExtraThread);

    public void removeSubSystem(SubSystem system);

    public void run();

}
