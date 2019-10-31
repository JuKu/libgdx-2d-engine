package com.jukusoft.engine2d.view.assets.zip;

import com.badlogic.gdx.files.FileHandle;
import com.jukusoft.engine2d.view.LibGDXTest;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArchiveFileHandleTest extends LibGDXTest {

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
        try {
            new ArchiveFileHandle(new ZipFile("not-existing-file.zip"), "mod.json");
        } catch (FileNotFoundException e) {
            throw new NoSuchFileException(e.getLocalizedMessage());
        }
    }

    @Test
    public void testPath() throws IOException {
        FileHandle handle = new ArchiveFileHandle(new ZipFile("../data/mods/mod1.zip"), "mod.json");
        assertNotNull(handle);

        assertEquals("mod.json", handle.path());
        assertEquals("", handle.parent().path());
    }

    @Test
    public void testParentPath() throws IOException {
        FileHandle handle = new ArchiveFileHandle(new ZipFile("../data/junit/test-zip.zip"), "dir1/400x200.png");
        assertNotNull(handle);

        assertEquals("dir1/400x200.png", handle.path());
        assertEquals("dir1", handle.parent().path());
    }

}
