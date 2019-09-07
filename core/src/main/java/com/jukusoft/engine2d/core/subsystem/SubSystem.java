package com.jukusoft.engine2d.core.subsystem;

import com.jukusoft.engine2d.core.init.Initializer;

import java.util.List;

public interface SubSystem {

    public void init(List<Initializer> initializers);

    public void update();

    public void shutdown();

}
