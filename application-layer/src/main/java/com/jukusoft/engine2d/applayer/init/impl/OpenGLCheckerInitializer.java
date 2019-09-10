package com.jukusoft.engine2d.applayer.init.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.GLVersion;
import com.jukusoft.engine2d.applayer.init.InitBeforeSplashScreen;
import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.shutdown.ErrorHandler;
import com.jukusoft.engine2d.core.utils.Utils;

@InitPriority(4)
@InitBeforeSplashScreen
public class OpenGLCheckerInitializer implements Initializer {

    private static final String OPENGL_SECTION = "OpenGL";
    private static final String LOG_TAG = "OpenGL";

    @Override
    public void init() throws Exception {
        Utils.printSection("OpenGL");

        GLVersion glVersion = Gdx.graphics.getGLVersion();
        Log.i(LOG_TAG, "Version: " + glVersion.getMajorVersion() + "." + glVersion.getMinorVersion() + "." + glVersion.getReleaseVersion());
        Log.i(LOG_TAG, "Vendor: " + glVersion.getVendorString());
        Log.i(LOG_TAG, "Renderer: " + glVersion.getRendererString());
        Log.i(LOG_TAG, "Type: " + glVersion.getType().name());

        //check OpenGL version
        int requiredMajorVersion = Config.getInt(OPENGL_SECTION, "requiredMajorVersion");
        int requiredMinorVersion = Config.getInt(OPENGL_SECTION, "requiredMinorVersion");
        Log.i(LOG_TAG, "check OpenGL version");

        if (!Gdx.graphics.getGLVersion().isVersionEqualToOrHigher(requiredMajorVersion, requiredMinorVersion)) {
            ErrorHandler.shutdownWithException(new RuntimeException("required OpenGL version is not available. required version: " + requiredMajorVersion + "." + requiredMinorVersion));
        }

        //check OpenGL extensions
        String requiredExtensions = Config.get(OPENGL_SECTION, "requiredOpenGLExtensions");
        final String[] extensions = requiredExtensions.split(";");

        //check, if required extensions are available
        for (String extension : extensions) {
            if (extension.equals("none")) {
                continue;
            }

            Log.d(LOG_TAG, "check OpenGL extension: " + extension);

            if (!Gdx.graphics.supportsExtension(extension)) {
                ErrorHandler.shutdownWithException(new RuntimeException("Required OpenGL extension is not available: " + extension));
            }
        }
    }

}
