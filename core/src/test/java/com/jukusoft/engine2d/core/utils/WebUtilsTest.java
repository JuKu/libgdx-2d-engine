package com.jukusoft.engine2d.core.utils;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class WebUtilsTest {

    @Test
    public void testConstructor() {
        new WebUtils();
    }

    @Test
    @Ignore
    public void testReadContentFromWebsite() throws IOException {
        assertEquals("my-test-content", WebUtils.readContentFromWebsite("http://mmo.jukusoft.com/api/junit-test-file.txt"));
    }

    @Test
    public void testNullEncoding() throws IOException {
        byte[] array = new byte[8];
        InputStream inputStream = new ByteArrayInputStream(array);

        WebUtils.createString(inputStream, null);
    }

    @Test
    public void testEncoding() throws IOException {
        byte[] array = new byte[8];
        InputStream inputStream = new ByteArrayInputStream(array);

        WebUtils.createString(inputStream, "ASCII");
    }

}
