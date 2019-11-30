package com.jukusoft.engine2d.basegame.mods.credits;

import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CreditsParserTest {

    @Test
    public void testConstructor() {
        new CreditsParser();
    }

    @Test(expected = NullPointerException.class)
    public void testParseNullModName() {
        CreditsParser parser = new CreditsParser();
        parser.parse(null, "");
    }

    @Test(expected = NullPointerException.class)
    public void testParseNullContent() {
        CreditsParser parser = new CreditsParser();

        String content = null;
        parser.parse("testmod", content);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyContent() {
        CreditsParser parser = new CreditsParser();
        parser.parse("testmod", "");
    }

    @Test(expected = NullPointerException.class)
    public void testParseNullInputStream() {
        CreditsParser parser = new CreditsParser();

        InputStream is = null;
        parser.parse("testmod", is);
    }

    @Test
    public void testParse() {
        String content = "[" +
                "   {" +
                "       \"path\": \"test/image.png\"," +
                "       \"title\": \"image1\"," +
                "       \"author\": \"JuKuSoft\"," +
                "       \"url\": \"http://jukusoft.com\"," +
                "       \"license\": \"test\"," +
                "   }," +"   {" +
                "       \"path\": \"test/image2.png\"," +
                "       \"title\": \"image2\"," +
                "       \"author\": \"Max Mustermann\"," +
                "       \"url\": \"http://example.com\"," +
                "       \"license\": \"test2\"," +
                "   }," +"   {" +
                "       \"path\": \"test/image3.png\"," +
                "       \"title\": \"image3\"," +
                "       \"author\": \"test\"," +
                "       \"url\": \"http://example2.com\"," +
                "       \"license\": \"test3\"," +
                "   }" +
                "]";

        CreditsParser parser = new CreditsParser();
        List<CreditEntry> list = parser.parse("testmod", content);

        assertFalse(list.isEmpty());
        assertEquals(3, list.size());

        CreditEntry entry = list.get(0);
        assertEquals("testmod", entry.getModName());
        assertEquals("test/image.png", entry.getPath());
        assertEquals("image1", entry.getTitle());
        assertEquals("JuKuSoft", entry.getAuthor());
        assertEquals("http://jukusoft.com", entry.getUrl());
        assertEquals("test", entry.getLicense());

        entry = list.get(1);
        assertEquals("testmod", entry.getModName());
        assertEquals("test/image2.png", entry.getPath());
        assertEquals("image2", entry.getTitle());
        assertEquals("Max Mustermann", entry.getAuthor());
        assertEquals("http://example.com", entry.getUrl());
        assertEquals("test2", entry.getLicense());

        entry = list.get(2);
        assertEquals("testmod", entry.getModName());
        assertEquals("test/image3.png", entry.getPath());
        assertEquals("image3", entry.getTitle());
        assertEquals("test", entry.getAuthor());
        assertEquals("http://example2.com", entry.getUrl());
        assertEquals("test3", entry.getLicense());
    }

}
