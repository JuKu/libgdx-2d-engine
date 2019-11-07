package com.jukusoft.engine2d.view.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.jukusoft.engine2d.view.LibGDXTest;
import org.junit.Test;
import org.mini2Dx.gdx.utils.Array;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AssetLoadingFromMultipleZipFilesTest extends LibGDXTest {

    @Test(expected = GdxRuntimeException.class)
    public void testLoadNotExistingAsset() throws IOException {
        Array<ZipFile> zipFiles = new Array<>();
        zipFiles.add(new ZipFile("../data/junit/test-zip.zip"));
        AssetManager assetManager = ZipAssetManagerFactory.create(zipFiles);
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
        Array<ZipFile> zipFiles = new Array<>();
        zipFiles.add(new ZipFile("../data/junit/test-zip.zip"));

        AssetManager assetManager = ZipAssetManagerFactory.create(zipFiles);
        assetManager.setErrorListener((asset, throwable) -> {
            System.err.println("Error while loading asset: " + asset);
            throwable.printStackTrace();
        });

        assetManager.load(fileName, Texture.class);
        assetManager.finishLoadingAsset(fileName);

        return assetManager.get(fileName);
    }

    private Texture loadTextureFromZipArray(Array<ZipFile> zipFiles, String fileName) throws IOException {
        AssetManager assetManager = ZipAssetManagerFactory.create(zipFiles);
        assetManager.setErrorListener((asset, throwable) -> {
            System.err.println("Error while loading asset: " + asset);
            throwable.printStackTrace();
        });

        assetManager.load(fileName, Texture.class);
        assetManager.finishLoadingAsset(fileName);

        return assetManager.get(fileName);
    }

    /**
     * this test verifys, that the atlas is correct
     */
    @Test(timeout = 5000)
    public void testLoadTextureAtlasDirectly() throws IOException {
        AssetManager assetManager = new AssetManager();
        assetManager.setErrorListener((asset, throwable) -> {
            System.err.println("Error while loading asset: " + asset);
            throwable.printStackTrace();
        });

        String fileName = "../data/junit/loadscreen/AnimationLoadingScreen.pack";
        assertTrue(new File(fileName).exists());

        assetManager.load(fileName, TextureAtlas.class);
        assetManager.finishLoadingAsset(fileName);
        TextureAtlas textureAtlas = assetManager.get(fileName);
        assertNotNull(textureAtlas);

        assertEquals(5, textureAtlas.getTextures().size);

        for (Texture texture : textureAtlas.getTextures()) {
            assertEquals(1024, texture.getWidth());
            assertEquals(1024, texture.getHeight());
        }
    }

    @Test(timeout = 5000)
    public void testLoadTextureAtlasFromZip() throws IOException {
        TextureAtlas textureAtlas = loadTextureAtlas("../data/junit/test-zip.zip", "AnimationLoadingScreen.pack");
        assertNotNull(textureAtlas);

        assertEquals(5, textureAtlas.getTextures().size);

        for (Texture texture : textureAtlas.getTextures()) {
            assertEquals(1024, texture.getWidth());
            assertEquals(1024, texture.getHeight());
        }
    }

    @Test(timeout = 5000)
    public void testLoadTextureAtlasFromDirInZip() throws IOException {
        TextureAtlas textureAtlas = loadTextureAtlas("../data/junit/test-zip.zip", "loadscreen/AnimationLoadingScreen.pack");
        assertNotNull(textureAtlas);

        assertEquals(5, textureAtlas.getTextures().size);

        for (Texture texture : textureAtlas.getTextures()) {
            assertEquals(1024, texture.getWidth());
            assertEquals(1024, texture.getHeight());
        }
    }

    @Test(timeout = 5000)
    public void testLoadTextureAtlasFromDirInZip1() throws IOException {
        TextureAtlas textureAtlas = loadTextureAtlas("../data/junit/test-zip2.zip", "loadscreen/AnimationLoadingScreen.pack");
        assertNotNull(textureAtlas);

        assertEquals(5, textureAtlas.getTextures().size);

        for (Texture texture : textureAtlas.getTextures()) {
            assertEquals(1024, texture.getWidth());
            assertEquals(1024, texture.getHeight());
        }
    }

    private TextureAtlas loadTextureAtlas(String zipPath, String fileName) throws IOException {
        Array<ZipFile> zipFiles = new Array<>();
        zipFiles.add(new ZipFile(zipPath));

        AssetManager assetManager = ZipAssetManagerFactory.create(zipFiles);
        assetManager.setErrorListener((asset, throwable) -> {
            System.err.println("Error while loading asset: " + asset);
            throwable.printStackTrace();
        });

        assetManager.load(fileName, TextureAtlas.class);
        assetManager.finishLoadingAsset(fileName);
        return assetManager.get(fileName);
    }

    private TextureAtlas loadTextureAtlasFromZipArray(Array<ZipFile> zipFiles, String fileName) throws IOException {
        AssetManager assetManager = ZipAssetManagerFactory.create(zipFiles);
        assetManager.setErrorListener((asset, throwable) -> {
            System.err.println("Error while loading asset: " + asset);
            throwable.printStackTrace();
        });

        assetManager.load(fileName, TextureAtlas.class);
        assetManager.finishLoadingAsset(fileName);
        return assetManager.get(fileName);
    }

    @Test(timeout = 5000)
    public void testLoadAssetsWithZipArray() throws IOException {
        Array<ZipFile> zipFiles = new Array<>();
        zipFiles.add(new ZipFile("../data/junit/test-zip2.zip"));
        zipFiles.add(new ZipFile("../data/junit/test-zip3.zip"));

        TextureAtlas textureAtlas = loadTextureAtlasFromZipArray(zipFiles, "loadscreen/AnimationLoadingScreen.pack");
        assertNotNull(textureAtlas);

        assertEquals(5, textureAtlas.getTextures().size);

        for (Texture texture : textureAtlas.getTextures()) {
            assertEquals(1024, texture.getWidth());
            assertEquals(1024, texture.getHeight());
        }

        //load another asset, but from a other zip file
        Texture texture = loadTextureFromZipArray(zipFiles, "placeholderdir/placeholder2.png");
        assertNotNull(texture);
        assertEquals(640, texture.getWidth());
        assertEquals(360, texture.getHeight());
    }

}
