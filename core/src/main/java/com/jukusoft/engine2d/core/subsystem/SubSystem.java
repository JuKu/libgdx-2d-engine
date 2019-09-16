package com.jukusoft.engine2d.core.subsystem;

public interface SubSystem {

    //public void init(List<Initializer> initializers);

    public void init();

    public void update();

    public void shutdown();

}
