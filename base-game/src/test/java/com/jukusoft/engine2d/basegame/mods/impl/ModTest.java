package com.jukusoft.engine2d.basegame.mods.impl;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ModTest {

    @Test
    public void testConstructor() {
        Mod mod = new Mod(
                "testname",
                "test title",
                "test description",
                new HashSet<>(),
                "1.0.0"
        );
    }

    @Test
    public void testGetterAndSetter() {
        Mod mod = new Mod(
                "testname",
                "test title",
                "test description",
                new HashSet<>(),
                "1.0.0"
        );

        mod.addDependency("core", "1.0.1");
        mod.setUrl("http://jukusoft.com");

        assertEquals("testname", mod.getName());
        assertEquals("test title", mod.getTitle());
        assertEquals("test description", mod.getDescription());
        assertTrue(mod.getTypes().isEmpty());
        assertEquals("1.0.0", mod.getVersion());

        assertFalse(mod.hasType(Mod.Type.ASSETPACK));

        assertEquals("1.0.1", mod.listDependencies().get("core"));
        assertEquals("http://jukusoft.com", mod.getUrl());
    }

    @Test
    public void testHasType() {
        Set<String> types = new HashSet<>();
        types.add(Mod.Type.ASSETPACK.name());
        types.add(Mod.Type.DLC.name());
        types.add(Mod.Type.GAMEPACK.name());

        Mod mod = new Mod(
                "testname",
                "test title",
                "test description",
                types,
                "1.0.0"
        );

        assertFalse(mod.hasType(Mod.Type.MOD));
        assertFalse(mod.hasType(Mod.Type.PATCH));
        assertTrue(mod.hasType(Mod.Type.DLC));
        assertTrue(mod.hasType(Mod.Type.GAMEPACK));
        assertTrue(mod.hasType(Mod.Type.ASSETPACK));
    }

}
