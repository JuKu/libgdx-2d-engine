package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;

import java.util.function.Consumer;

@InitPriority(9)
public class SubSystemCreatorInitializer implements Initializer {

    private final SubSystemManager subSystemManager;
    private final Consumer<SubSystemManager> subSystemCreator;

    public SubSystemCreatorInitializer(SubSystemManager subSystemManager, Consumer<SubSystemManager> subSystemCreator) {
        this.subSystemManager = subSystemManager;
        this.subSystemCreator = subSystemCreator;
    }

    @Override
    public void init() throws Exception {
        this.subSystemCreator.accept(this.subSystemManager);
    }

}
