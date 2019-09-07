package com.jukusoft.engine2d.core.config;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ConfigTest {

    @Test
    public void testConstructor() {
        new Config();
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadNotExistentFile() throws IOException {
        Config.load(new File("not-existent-file"));
    }

    @Test
    public void testLoad() throws IOException {
        Config.load(new File("../config/junit-logger.cfg"));

        assertEquals(true, Config.getBool("Logger", "enabled"));
        assertEquals(true, Config.getBool("Logger", "printToConsole"));
        assertEquals("VERBOSE", Config.get("Logger", "level"));
        assertEquals(10, Config.getInt("Logger", "testInt"));
        assertEquals(1.2f, Config.getFloat("Logger", "testFloat"), 0.0001f);

        //test reload
        Config.reload();
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadDirAsFile() throws IOException {
        Config.load(new File("../config/"));
    }

    @Test
    public void testLoadExampleConfig() throws IOException {
        Config.load(new File("../data/junit/exampleconfig/test.example.cfg"), true);
        Config.load(new File("../data/junit/exampleconfig/test.travis.cfg"), true);
    }

    /*@Test
    public void testLoadFileHandle () throws IOException {
        Config.load(new FileHandle("../config/junit-logger.cfg"));
    }

    @Test (expected = IllegalStateException.class)
    public void testLoadFileWithDir () throws IOException {
        Config.load(new FileHandle("../"));
    }*/

    @Test(expected = IllegalStateException.class)
    public void testGetNotExistentOption() {
        Config.get("test", "not-existent-key");
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadNotExistentDir() throws IOException {
        Config.loadDir(new File("../not-existent-dir/"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadDirWithFile() throws IOException {
        Config.loadDir(new File("../config/game.cfg"));
    }

    @Test
    public void testLoadDir() throws IOException {
        Config.loadDir(new File("../config/"));
    }

    @Test
    public void testLoadDir1() throws IOException {
        new File("../data/junit/config/test/").mkdirs();

        Config.loadDir(new File("../data/junit/config/"));
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadNotExistingConfigFromResource() throws IOException {
        Config.clear();
        Config.loadFromResource("not-existing-config.cfg", Config.class);
    }

    @Test
    public void testLoadFromResource() throws IOException {
        Config.clear();
        Config.loadFromResource("config/defaultConfig.cfg", Config.class);

        assertEquals("test2", Config.get("Default", "test"));
    }

    @Test
    public void testLoadFromResourceDir() throws IOException {
        Config.clear();
        Config.loadFromResourceDir("config", Config.class);

        assertEquals("test2", Config.get("Default", "test"));
    }

    @Test
    public void testGetOrDefault() {
        assertEquals("defaultValue", Config.getOrDefault("section", "not-existent-key", "defaultValue"));

        //set value
        Config.values.put("section.not-existent-key", "test-value");

        assertEquals("test-value", Config.getOrDefault("section", "not-existent-key", "defaultValue"));
    }

}
