package com.jukusoft.engine2d.core.utils;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {

    @Test(expected = NullPointerException.class)
    public void testCheckNullString() {
        StringUtils.checkNotNullAndNotEmpty(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckEmptyString() {
        StringUtils.checkNotNullAndNotEmpty("");
    }

    @Test
    public void testCheckValidString() {
        StringUtils.checkNotNullAndNotEmpty("valid string");
    }

    @Test(expected = NullPointerException.class)
    public void testCheckNotNullAndNotEmpty() {
        StringUtils.checkNotNullAndNotEmpty(null, "message object");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckNotNullAndNotEmpty1() {
        StringUtils.checkNotNullAndNotEmpty("", "message object");
    }

    @Test
    public void testCheckNotNullAndNotEmptyValidString() {
        StringUtils.checkNotNullAndNotEmpty("valid string", "message object");
    }

    @Test(expected = NullPointerException.class)
    public void testGetStringFromNullInputStream() {
        StringUtils.getStringFromInputStream(null);
    }

    @Test
    public void testGetStringFromInputStream() {
        InputStream is = new ByteArrayInputStream("test".getBytes());
        String content = StringUtils.getStringFromInputStream(is);

        assertEquals("test", content);
    }

}
