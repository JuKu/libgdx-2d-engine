package com.jukusoft.engine2d.ui.parser;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.core.utils.FileUtils;
import com.jukusoft.engine2d.ui.UIScreen;
import com.jukusoft.engine2d.view.assets.zip.ArchiveFileHandle;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipFile;

import static org.junit.Assert.*;

public class UIXMLParserImplTest {

    @Test
    public void testConstructor() {
        new UIXMLParserImpl();
    }

    @Test
    public void testParseFile() throws URISyntaxException, IOException {
        File xmlFile = FileUtils.getResourceAsFile("testscreen.xml");

        File baseDir = FileUtils.getResourceAsFile("");

        UIXMLParser parser = new UIXMLParserImpl();
        Array<UIScreen> screens = parser.parse(baseDir, FileUtils.readFile(xmlFile.getAbsolutePath(), StandardCharsets.UTF_8));
        assertNotNull(screens);
        assertFalse(screens.isEmpty());

        checkScreens(screens);
    }

    @Test
    public void testParseFileInZipArchiv() throws URISyntaxException, IOException {
        ZipFile zipFile = new ZipFile(FileUtils.getResourceAsFile("testscreen.zip"));
        FileHandle fileHandle = new ArchiveFileHandle(zipFile, "testscreen.xml");

        UIXMLParser parser = new UIXMLParserImpl();
        Array<UIScreen> screens = parser.parse(fileHandle);

        checkScreens(screens);
    }

    protected void checkScreens(Array<UIScreen> screens) {
        assertNotNull(screens);
        assertFalse(screens.isEmpty());

        assertEquals(1, screens.size);

        //get first screen
        UIScreen screen = screens.get(0);
        assertNotNull(screen);

        assertEquals("testscreen", screen.getId());
        assertEquals("wallpaper/bg.png", screen.getBackground());

        assertEquals(0, screen.getPosX());
        assertEquals(0, screen.getPosY());
        assertEquals("parent", screen.getWidth());
        assertEquals("parent", screen.getHeight());

        //check styles
        assertEquals(2, screen.listStyles().size);
        assertEquals("style.xml", screen.listStyles().get(0));
        assertEquals("styles/style1.xml", screen.listStyles().get(1));

        //TODO: add code here
    }

}
