package com.jukusoft.engine2d.applayer.utils;

import com.badlogic.gdx.utils.Array;
import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.subsystem.SubSystem;

import java.util.Objects;

public class SubSystemInitializer {

    //to avoid initializing subsystems twice
    private static Array<SubSystem> initializedSubSystems = new Array<>(10);

    private SubSystemInitializer() {
        //
    }

    public static void init(ObjectArrayList<SubSystem> subSystemList) {
        Objects.requireNonNull(subSystemList);
        Log.d(SubSystemInitializer.class.getSimpleName(), "initialize " + subSystemList.size() + " subsystems now...");

        for (int i = 0; i < subSystemList.size(); i++) {
            SubSystem subSystem = subSystemList.get(i);

            if (initializedSubSystems.contains(subSystem, false)) {
                throw new IllegalStateException("subsystem " + subSystem.getClass().getSimpleName() + " was already initialized by this SubSystemInitializer");
            }

            Log.d(SubSystemInitializer.class.getSimpleName(), "initialize subsystem: " + subSystem.getClass().getSimpleName());
            subSystem.init();

            initializedSubSystems.add(subSystem);
        }
    }

}
