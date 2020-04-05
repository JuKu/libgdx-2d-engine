package com.jukusoft.engine2d.core.version;

import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.WebUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * this class is responsible for getting newest version of the game from a website json file
 */
public class VersionChecker {

    private static VersionChecker instance = null;
    private Map<String,Version> cachedVersions = new HashMap<>(10);

    private VersionChecker() {
        //
    }

    protected static VersionChecker getInstance() {
        if (instance == null) {
            instance = new VersionChecker();
        }

        return instance;
    }

    /**
     * get newest version from cache or download newest version from website
     *
     * @return newest version
     */
    public static Version getNewestVersion() {
        //get url from config
        String url = Config.get("Updater", "getCurrentVersionUrl");

        //get cached version, if available
        if (VersionChecker.getInstance().cachedVersions.containsKey(url)) {
            return VersionChecker.getInstance().cachedVersions.get(url);
        }

        String jsonStr;

        //download json content
        try {
            jsonStr = WebUtils.readContentFromWebsite(url);
        } catch (IOException e) {
            Log.e(VersionChecker.class.getSimpleName(), "IOException while downloading version information from server: " + url, e);
            return new Version();
        }

        Version version = new Version();
        version.loadFromJson(jsonStr);

        //add version to cache
        VersionChecker.getInstance().cachedVersions.put(url, version);

        return version;
    }

    public static void clearCache() {
        VersionChecker.getInstance().cachedVersions.clear();
    }

}
