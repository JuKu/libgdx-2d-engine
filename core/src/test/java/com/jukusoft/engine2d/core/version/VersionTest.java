package com.jukusoft.engine2d.core.version;

import org.junit.Test;

import static org.junit.Assert.*;

public class VersionTest {

    @Test
    public void testConstructor() {
        new Version(VersionTest.class);
    }

    @Test
    public void testConstructor1() {
        new Version();
    }

    @Test(expected = NullPointerException.class)
    public void testNullConstructor() {
        new Version(null);
    }

    @Test
    public void testGetter() {
        Version version = new Version(String.class);

        //check required attributes which are represent in D:\Program Files\Java\jdk1.8.0_91\jre\lib\rt.jar
        //assertEquals(false, version.getVersion().contains("n/a"));
        assertEquals(true, version.getRevision().contains("n/a"));
        assertEquals(true, version.getBuildJdk().contains("n/a"));
        assertEquals(true, version.getBuildTime().contains("n/a"));
        // assertEquals(false, version.getCreatedBy().contains("n/a"));
        assertEquals(true, version.getVendorID().contains("n/a"));
        //assertEquals(false, version.getVendor().contains("n/a"));
        assertEquals(false, version.getFullVersion().isEmpty());
    }

    @Test
    public void testGetInstance() {
        assertNull(Version.getInstance());

        Version version = new Version(String.class);
        Version.setInstance(version);
        assertNotNull(Version.getInstance());

        //check, that instances are equals
        assertEquals(version, Version.getInstance());
    }

    @Test (expected = NullPointerException.class)
    public void testLoadFromNullJson() {
        new Version().loadFromJson(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLoadFromEmptyJson() {
        new Version().loadFromJson("");
    }

    @Test
    public void testLoadFromJson() {
        String jsonStr = "{" +
                "revision: \"1\"," +
                "version: \"1.2.3\"," +
                "buildJdk: \"test\"," +
                "buildTime: \"n/a\"," +
                "createdBy: \"JuKuSoft\"," +
                "vendorID: \"com.jukusoft\"," +
                "vendor: \"test2\"" +
                "}";

        Version version = new Version();
        version.loadFromJson(jsonStr);

        assertEquals("1", version.getRevision());
        assertEquals("1.2.3", version.getVersion());
        assertEquals("test", version.getBuildJdk());
        assertEquals("n/a", version.getBuildTime());
        assertEquals("JuKuSoft", version.getCreatedBy());
        assertEquals("com.jukusoft", version.getVendorID());
        assertEquals("test2", version.getVendor());
    }

}
