package com.jukusoft.engine2d.view.assets.zip;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.zip.ZipFile;

public class ArchiveFileHandleTest {

    @Test(expected = NullPointerException.class)
    public void testNullArchiveConstructor() {
        new ArchiveFileHandle(null, "test");
    }

    @Test(expected = NullPointerException.class)
    public void testNullArchiveConstructor1() {
        new ArchiveFileHandle(null, new File("test.txt"));
    }

    @Test(expected = NullPointerException.class)
    public void testNullFileConstructor() throws IOException {
        File file = null;
        new ArchiveFileHandle(new ZipFile("../data/mods/mod1.zip"), file);
    }

    @Test(expected = NullPointerException.class)
    public void testNullFileNameConstructor() throws IOException {
        String fileName = null;
        new ArchiveFileHandle(new ZipFile("../data/mods/mod1.zip"), fileName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFileNameConstructor() throws IOException {
        new ArchiveFileHandle(new ZipFile("../data/mods/mod1.zip"), "");
    }

    @Test(expected = NoSuchFileException.class)
    public void testNotExistingZipFileConstructor() throws IOException {
        new ArchiveFileHandle(new ZipFile("not-existing-file.zip"), "mod.json");
    }

}
