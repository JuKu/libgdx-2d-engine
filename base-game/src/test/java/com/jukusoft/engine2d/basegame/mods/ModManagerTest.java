package com.jukusoft.engine2d.basegame.mods;

import com.jukusoft.engine2d.basegame.mods.credits.CreditEntry;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModManagerTest {

    @Test
    public void testGetInstance() {
        ModManager modManager = ModManager.getInstance();
        assertNotNull(modManager);
    }

    @Test
    public void testListCredits() {
        List<CreditEntry> list = ModManager.getInstance().listCredits();

        //TODO: add code here
    }

}
