package com.jukusoft.engine2d.basegame.mods.impl;

import com.jukusoft.engine2d.basegame.mods.ModLoader;
import com.jukusoft.engine2d.basegame.mods.ModManager;
import com.jukusoft.engine2d.core.logger.Log;
import org.mini2Dx.gdx.utils.Array;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * singleton default mod manager
 */
public class DefaultModManager implements ModManager {

    private static final String LOG_TAG = "ModManager";
    private static DefaultModManager instance;

    private Array<Mod> mods = new Array<>(10);

    private DefaultModManager() {
        //
    }

    @Override
    public void loadFromDir(File modDir) {
        Log.d(LOG_TAG, "load mods from dir: " + modDir.getAbsolutePath());

        ModLoader modLoader = new ZipModLoader();

        try {
            List<Mod> modList = modLoader.findMods(modDir);
            Log.i(LOG_TAG, String.format("{} mods found in directory: {}", modList.size(), modDir.getAbsolutePath()));
            mods.addAll(modList.toArray(new Mod[0]));
        } catch (IOException e) {
            Log.w(LOG_TAG, "IOException while loading mods: ", e);
        }
    }

    @Override
    public Array<Mod> listMods() {
        return mods;
    }

    public static ModManager getInstance() {
        if (instance == null) {
            instance = new DefaultModManager();
        }

        return instance;
    }

}
