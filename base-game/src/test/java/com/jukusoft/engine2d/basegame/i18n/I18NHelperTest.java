package com.jukusoft.engine2d.basegame.i18n;

import com.jukusoft.engine2d.core.config.Config;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class I18NHelperTest {

    @BeforeClass
    public static void beforeClass() {
        Config.set("I18N", "dir", "../data/i18n");
        Config.set("I18N", "token", "en");
    }

    @AfterClass
    public static void afterClass() {
        Config.clear();
    }

    @Test
    public void testGetI18NDirPath() {
        String dir = I18NHelper.getI18NDirPath();
        assertNotNull(dir);
        assertFalse(dir.isEmpty());
        assertEquals("../data/i18n", dir);

        assertTrue(new File(dir).exists());
        assertTrue(new File(dir).isDirectory());
    }

    @Test
    public void testGetI18NDir() {
        File dir = I18NHelper.getI18NDir();
        assertTrue(dir.exists());
        assertTrue(dir.isDirectory());
        assertTrue(dir.getAbsolutePath().replace("\\", "/").endsWith("/i18n"));
    }

    @Test
    public void testGetCurrentLanguageToken() {
        assertEquals("en", I18NHelper.getCurrentLanguageToken());

        Config.set("I18N", "token", "de");
        assertEquals("de", I18NHelper.getCurrentLanguageToken());
    }

    @Test(expected = NullPointerException.class)
    public void testSetNullLanguageToken() {
        I18NHelper.setLanguage(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyLanguageToken() {
        I18NHelper.setLanguage("");
    }

    @Test(expected = IllegalStateException.class)
    public void testSetInvalidLanguageToken() {
        I18NHelper.setLanguage("not-existent-token");
    }

    @Test
    public void testSetLanguageToken() {
        I18NHelper.setLanguage("en");
        assertEquals("en", Config.get("I18N", "token"));

        I18NHelper.setLanguage("de");
        assertEquals("de", Config.get("I18N", "token"));
    }

    @Test
    public void testListAvailableLanguagePacks() {
        List<LanguagePack> list = I18NHelper.listAvailableLanguagePacks();
        assertNotNull(list);
        assertFalse(list.isEmpty());

        assertEquals(2, list.size());

        List<String> tokens = list.stream()
                .map(langPack -> langPack.getToken())
                .collect(Collectors.toList());

        assertTrue(tokens.contains("en"));
        assertTrue(tokens.contains("de"));

        LanguagePack enPack = list.stream()
                .filter(langPack -> langPack.getToken().equals("en"))
                .findFirst()
                .get();

        LanguagePack dePack = list.stream()
                .filter(langPack -> langPack.getToken().equals("de"))
                .findFirst()
                .get();

        assertEquals("en", enPack.getToken());
        assertEquals("English", enPack.getTitle());

        assertEquals("de", dePack.getToken());
        assertEquals("German", dePack.getTitle());
    }

}
