package com.jukusoft.engine2d.view.assets.zip;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.jukusoft.engine2d.view.GdxTestRunner;
import com.jukusoft.engine2d.view.LibGDXTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class FileHandleTest extends LibGDXTest {

    //https://github.com/TomGrill/gdx-testing

    @Test
    public void testConstructor() {
        FileHandle handle = Gdx.files.absolute("../data/mods/mod1.zip");
        assertNotNull(handle);

        assertEquals("zip", handle.extension());
        assertEquals("mod1", handle.nameWithoutExtension());
        assertEquals("mod1.zip", handle.name());
        assertEquals("../data/mods/test", handle.sibling("test").path());
        assertEquals("../data/mods/mod1.zip/test2", handle.child("test2").path());
        assertEquals("../data/mods", handle.parent().path());

        assertNotNull(handle.read());
        assertTrue(handle.readBytes().length > 0);
    }

}
