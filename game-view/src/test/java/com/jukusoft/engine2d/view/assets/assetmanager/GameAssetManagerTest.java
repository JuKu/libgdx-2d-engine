package com.jukusoft.engine2d.view.assets.assetmanager;

import com.jukusoft.engine2d.basegame.mods.ModManager;
import com.jukusoft.engine2d.basegame.mods.impl.Mod;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GameAssetManagerTest {

    @BeforeClass
    public static void beforeClass() {
        Log.initJUnitLogger(Log.LEVEL.DEBUG);
    }

    @AfterClass
    public static void afterClass() {
        Log.shutdown();
    }

    @Test
    public void testGetInstance() {
        Config.set("Mods", "extensions", ".zip,.mod,.gamepack,.package");
        ModManager.getInstance().loadFromDir(new File("../data/mods"));
        assertEquals(1, ModManager.getInstance().listMods().size);
        assertEquals(1, ModManager.getInstance().listMods(Mod.Type.ASSETPACK).size);

        GameAssetManager assetManager = GameAssetManager.getInstance();
        assertNotNull(assetManager);

        Log.shutdown();
    }

}
