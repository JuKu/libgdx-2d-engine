package com.jukusoft.engine2d.view.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.jukusoft.engine2d.view.LibGDXTest;
import org.junit.Test;

import java.io.IOException;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AssetLoadingFromZipTest extends LibGDXTest {

    @Test(expected = GdxRuntimeException.class)
    public void testLoadNotExistingAsset() throws IOException {
        AssetManager assetManager = ZipAssetManagerFactory.create(new ZipFile("../data/junit/test-zip.zip"));
        assetManager.load("not-existing-asset.png", Texture.class);
        assetManager.finishLoadingAsset("not-existing-asset.png");
    }

    @Test(timeout = 5000)
    public void testLoadTexture() throws IOException {
        Texture texture = loadTexture("placeholder.png");
        assertNotNull(texture);
        assertEquals(640, texture.getWidth());
        assertEquals(360, texture.getHeight());
    }

    @Test(timeout = 5000)
    public void testLoadTextureFromDirectoryInZip() throws IOException {
        Texture texture = loadTexture("dir1/400x200.png");
        assertNotNull(texture);
        assertEquals(400, texture.getWidth());
        assertEquals(200, texture.getHeight());
    }

    private Texture loadTexture(String fileName) throws IOException {
        AssetManager assetManager = ZipAssetManagerFactory.create(new ZipFile("../data/junit/test-zip.zip"));
        assetManager.setErrorListener((asset, throwable) -> {
            System.err.println("Error while loading asset: " + asset);
            throwable.printStackTrace();
        });

        assetManager.load(fileName, Texture.class);
        assetManager.finishLoadingAsset(fileName);

        return assetManager.get(fileName);
    }

}
