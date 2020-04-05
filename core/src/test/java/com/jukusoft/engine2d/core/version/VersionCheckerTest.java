package com.jukusoft.engine2d.core.version;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jukusoft.engine2d.core.config.Config;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;

public class VersionCheckerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort());

    @Test
    public void testGetNewestVersion() {
        int port = wireMockRule.port();

        Config.set("Updater", "getCurrentVersionUrl", "http://127.0.0.1:" + port + "/version.json");

        String jsonStr = "{" +
                "revision: \"1\"," +
                "version: \"1.2.3\"," +
                "buildJdk: \"test\"," +
                "buildTime: \"n/a\"," +
                "createdBy: \"JuKuSoft\"," +
                "vendorID: \"com.jukusoft\"," +
                "vendor: \"test2\"" +
                "}";

        //see also: http://wiremock.org/docs/getting-started/
        stubFor(get(urlEqualTo("/version.json"))
                //.withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody(jsonStr)));

        Version version = VersionChecker.getNewestVersion();
        assertEquals("1", version.getRevision());
        assertEquals("1.2.3", version.getVersion());
        assertEquals("test", version.getBuildJdk());
        assertEquals("n/a", version.getBuildTime());
        assertEquals("JuKuSoft", version.getCreatedBy());
        assertEquals("com.jukusoft", version.getVendorID());
        assertEquals("test2", version.getVendor());
    }

}
