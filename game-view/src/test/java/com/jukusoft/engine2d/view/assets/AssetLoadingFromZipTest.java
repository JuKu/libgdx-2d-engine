package com.jukusoft.engine2d.view.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.jukusoft.engine2d.view.LibGDXTest;
import org.junit.Test;

import java.io.IOException;
import java.util.zip.ZipFile;

public class AssetLoadingFromZipTest extends LibGDXTest {

    @Test(expected = GdxRuntimeException.class)
    public void testLoadNotExistingAsset() throws IOException {
        AssetManager assetManager = ZipAssetManagerFactory.create(new ZipFile("../data/junit/test-zip.zip"));
        assetManager.load("not-existing-asset.png", Texture.class);
        assetManager.finishLoadingAsset("not-existing-asset.png");
    }

}
