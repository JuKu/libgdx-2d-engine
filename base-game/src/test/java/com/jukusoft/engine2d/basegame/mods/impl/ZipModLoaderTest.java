package com.jukusoft.engine2d.basegame.mods.impl;

import com.jukusoft.engine2d.basegame.mods.ModLoader;
import com.jukusoft.engine2d.core.config.Config;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ZipModLoaderTest {

    @BeforeClass
    public static void beforeClass() {
        Config.set("Mods", "extensions", ".zip,.mod,.gamepack,.package");
    }

    @AfterClass
    public static void afterClass() {
        Config.clear();
    }

    @Test
    public void testConstructor() {
        new ZipModLoader();
    }

    @Test(expected = NullPointerException.class)
    public void testLoadModsFromNullDir() throws IOException {
        ModLoader modLoader = new ZipModLoader();
        modLoader.findMods(null);
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadModsFromNotExistingDir() throws IOException {
        ModLoader modLoader = new ZipModLoader();
        modLoader.findMods(new File("not-existing-dir"));
    }

    @Test
    public void testLoadMods() throws IOException {
        ModLoader modLoader = new ZipModLoader();
        List<Mod> list = modLoader.findMods(new File("../data/mods"));

        assertEquals(1, list.size());

        Mod firstMod = list.get(0);
        assertEquals("core", firstMod.getName());
        assertEquals("Core", firstMod.getTitle());
        assertEquals("the core game content", firstMod.getDescription());
        assertEquals("0.0.1", firstMod.getVersion());
        assertEquals("http://jukusoft.com", firstMod.getUrl());
        assertEquals(0, firstMod.listDependencies().size());
    }

}
