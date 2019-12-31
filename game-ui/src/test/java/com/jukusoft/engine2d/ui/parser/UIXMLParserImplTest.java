package com.jukusoft.engine2d.ui.parser;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.FileUtils;
import com.jukusoft.engine2d.ui.UIScreen;
import com.jukusoft.engine2d.ui.dto.Soundtrack;
import com.jukusoft.engine2d.view.assets.zip.ArchiveFileHandle;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipFile;

import static org.junit.Assert.*;

public class UIXMLParserImplTest {

    @BeforeClass
    public static void beforeClass() {
        Log.initJUnitLogger(Log.LEVEL.DEBUG);
    }

    @AfterClass
    public static void afterClass() {
        Log.shutdown();
    }

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

        //check soundtracks
        assertEquals(1, screen.listSoundtracks().size);
        Soundtrack soundtrack = screen.listSoundtracks().get(0);
        assertEquals("music1.ogg", soundtrack.getPath());
        assertEquals(true, soundtrack.isLoop());
        assertEquals(1.0f, soundtrack.getVolume(), 0.0001f);

        //TODO: add code here
    }

}
