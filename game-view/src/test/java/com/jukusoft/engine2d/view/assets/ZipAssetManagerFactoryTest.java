package com.jukusoft.engine2d.view.assets;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertTrue;

public class ZipAssetManagerFactoryTest {

    @Test(expected = NullPointerException.class)
    public void testCreateNull() {
        ZipAssetManagerFactory.create(null);
    }

    @Test(expected = NoSuchFileException.class)
    public void testCreateNotExistingZipFile() throws IOException {
        try {
            ZipAssetManagerFactory.create(new ZipFile("not-existing-file.zip"));
        } catch (FileNotFoundException e) {
            throw new NoSuchFileException(e.getLocalizedMessage());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateEmptyZip() throws IOException {
        assertTrue(new File("../data/junit/empty-zip.zip").exists());
        ZipAssetManagerFactory.create(new ZipFile("../data/junit/empty-zip.zip"));
    }

    @Test
    public void testCreateValidZip() throws IOException {
        assertTrue(new File("../data/mods/mod1.zip").exists());
        ZipAssetManagerFactory.create(new ZipFile("../data/mods/mod1.zip"));
    }

}
