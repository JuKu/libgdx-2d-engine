package com.jukusoft.engine2d.view.assets.assetmanager;

import com.jukusoft.engine2d.basegame.mods.ModManager;
import com.jukusoft.engine2d.core.config.Config;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GameAssetManagerTest {

    @Test
    public void testGetInstance() {
        Config.set("Mods", "extensions", ".zip,.mod,.gamepack,.package");
        ModManager.getInstance().loadFromDir(new File("../data/mods"));

        GameAssetManager assetManager = GameAssetManager.getInstance();
        assertNotNull(assetManager);
    }

}
