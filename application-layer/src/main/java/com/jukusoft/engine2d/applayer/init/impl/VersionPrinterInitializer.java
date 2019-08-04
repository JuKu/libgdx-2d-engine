package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.BaseApp;
import com.jukusoft.engine2d.applayer.init.InitBeforeSplashScreen;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.applayer.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.Utils;
import com.jukusoft.engine2d.core.version.Version;

@InitPriority(2)
@InitBeforeSplashScreen
public class VersionPrinterInitializer implements Initializer {

    protected static final String VERSION_TAG = "Version";

    private final Version version;

    public VersionPrinterInitializer(Version version) {
        this.version = version;
    }

    @Override
    public void init() {
        //set global version
        Version.setInstance(this.version);

        //print game engine version information
        Utils.printSection("Game Version");
        Log.i(VERSION_TAG, "Version: " + version.getVersion());
        Log.i(VERSION_TAG, "Build: " + version.getRevision());
        Log.i(VERSION_TAG, "Build JDK: " + version.getBuildJdk());
        Log.i(VERSION_TAG, "Build Time: " + version.getBuildTime());
        Log.i(VERSION_TAG, "Vendor ID: " + (!version.getVendor().equals("n/a") ? version.getVendor() : version.getVendorID()));

        //print game engine version information
        Utils.printSection("Game Engine");
        Version version1 = new Version(BaseApp.class);
        Log.i(VERSION_TAG, "Version: " + version1.getVersion());
        Log.i(VERSION_TAG, "Build: " + version1.getRevision());
        Log.i(VERSION_TAG, "Build JDK: " + version1.getBuildJdk());
        Log.i(VERSION_TAG, "Build Time: " + version1.getBuildTime());
        Log.i(VERSION_TAG, "Vendor ID: " + (!version1.getVendor().equals("n/a") ? version1.getVendor() : version1.getVendorID()));

        //print libGDX version
        Utils.printSection("libGDX");
        Log.i("libGDX", "libGDX Version: " + com.badlogic.gdx.Version.VERSION);

        //print java version
        Utils.printSection("Java Version");
        Log.i("Java", "Java Vendor: " + System.getProperty("java.vendor"));
        Log.i("Java", "Java Vendor URL: " + System.getProperty("java.vendor.url"));
        Log.i("Java", "Java Version: " + System.getProperty("java.version"));
    }

}
