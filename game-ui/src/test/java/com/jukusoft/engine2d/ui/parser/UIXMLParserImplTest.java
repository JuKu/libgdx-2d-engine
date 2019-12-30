package com.jukusoft.engine2d.ui.parser;

import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.core.utils.FileUtils;
import com.jukusoft.engine2d.ui.UIScreen;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

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
        Array<UIScreen> screens = parser.parse(baseDir, FileUtils.readFile(baseDir.getAbsolutePath(), StandardCharsets.UTF_8));
        assertNotNull(screens);
        assertFalse(screens.isEmpty());

        //TODO: add code here
    }

}
