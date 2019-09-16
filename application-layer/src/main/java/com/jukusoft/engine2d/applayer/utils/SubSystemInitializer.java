package com.jukusoft.engine2d.applayer.utils;

import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.basegame.subsystem.SubSystem;

import java.util.Objects;

public class SubSystemInitializer {

    private SubSystemInitializer() {
        //
    }

    public static void init(ObjectArrayList<SubSystem> subSystemList) {
        Objects.requireNonNull(subSystemList);

        for (int i = 0; i < subSystemList.size(); i++) {
            SubSystem subSystem = subSystemList.get(i);
            subSystem.init();

        }
    }

}
