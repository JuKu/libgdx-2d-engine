package com.jukusoft.engine2d.view.assets.assetmanager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GameAssetManagerTest {

    @Test
    public void testGetInstance() {
        GameAssetManager assetManager = GameAssetManager.getInstance();
        assertNotNull(assetManager);
    }

}
