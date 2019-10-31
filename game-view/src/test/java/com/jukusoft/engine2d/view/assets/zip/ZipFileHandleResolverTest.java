package com.jukusoft.engine2d.view.assets.zip;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import org.junit.Test;

import java.io.IOException;
import java.util.zip.ZipFile;

public class ZipFileHandleResolverTest {

    @Test(expected = NullPointerException.class)
    public void testNullConstructor() {
        new ZipFileHandleResolver(null);
    }

    @Test
    public void testConstructor() throws IOException {
        new ZipFileHandleResolver(new ZipFile("../data/mods/mod1.zip"));
    }

    @Test(expected = IllegalStateException.class)
    public void testResolveNotExistingFile() throws IOException {
        FileHandleResolver resolver = new ZipFileHandleResolver(new ZipFile("../data/mods/mod1.zip"));
        resolver.resolve("test.txt");
    }

    @Test
    public void testResolve() throws IOException {
        FileHandleResolver resolver = new ZipFileHandleResolver(new ZipFile("../data/mods/mod1.zip"));
        resolver.resolve("mod.json");
    }

}
