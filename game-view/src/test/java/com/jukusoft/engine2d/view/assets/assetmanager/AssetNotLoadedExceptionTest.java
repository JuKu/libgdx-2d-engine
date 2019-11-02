package com.jukusoft.engine2d.view.assets.assetmanager;

import org.junit.Test;

public class AssetNotLoadedExceptionTest {

    @Test(expected = NullPointerException.class)
    public void testNullConstructor() {
        new AssetNotLoadedException(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyConstructor() {
        new AssetNotLoadedException("");
    }

    @Test
    public void testConstructor() {
        new AssetNotLoadedException("test");
    }

}
