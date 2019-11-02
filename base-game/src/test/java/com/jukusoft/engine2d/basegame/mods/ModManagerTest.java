package com.jukusoft.engine2d.basegame.mods;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModManagerTest {

    @Test
    public void testGetInstance() {
        ModManager modManager = ModManager.getInstance();
        assertNotNull(modManager);
    }

}
