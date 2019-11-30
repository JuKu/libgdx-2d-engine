package com.jukusoft.engine2d.basegame.mods.impl;

import com.jukusoft.engine2d.basegame.mods.ModLoader;
import com.jukusoft.engine2d.basegame.mods.ModManager;
import com.jukusoft.engine2d.basegame.mods.credits.CreditEntry;
import com.jukusoft.engine2d.basegame.mods.credits.CreditsParser;
import com.jukusoft.engine2d.core.logger.Log;
import org.mini2Dx.gdx.utils.Array;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

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
            Log.i(LOG_TAG, String.format("%d mods found in directory: %s", modList.size(), modDir.getAbsolutePath()));
            mods.addAll(modList.toArray(new Mod[0]));

            if (mods.size == 0) {
                throw new IllegalStateException("no mod found in mod dir: " + modDir.getAbsolutePath());
            }
        } catch (IOException e) {
            Log.w(LOG_TAG, "IOException while loading mods: ", e);
        }
    }

    @Override
    public Array<Mod> listMods() {
        return mods;
    }

    @Override
    public Array<Mod> listMods(Mod.Type type) {
        Array<Mod> list = new Array<>(mods.size);

        for (Mod mod : mods) {
            if (mod.hasType(type)) {
                list.add(mod);
            }
        }

        return list;
    }

    @Override
    public List<CreditEntry> listCredits() {
        List<CreditEntry> list = new ArrayList<>();

        for (Mod mod : listMods()) {
            File file = mod.getArchiveFile();

            CreditsParser parser = new CreditsParser();

            try (ZipFile zipFile = new ZipFile(file)) {
                ZipEntry zipEntry = zipFile.getEntry("credits.json");

                if (zipEntry == null) {
                    Log.w(DefaultModManager.class.getSimpleName(), "Skip mod '" + mod.getName() + "', because zip does not contains credits.json file");
                    continue;
                }

                list.addAll(parser.parse(mod.getName(), zipFile.getInputStream(zipEntry)));
            } catch (ZipException e) {
                Log.w(DefaultModManager.class.getSimpleName(), "ZipException while opening mod zip file: " + file.getAbsolutePath());
            } catch (IOException e) {
                Log.w(DefaultModManager.class.getSimpleName(), "IOException while opening mod zip file: " + file.getAbsolutePath());
            }
        }

        return list;
    }

    public static ModManager getInstance() {
        if (instance == null) {
            instance = new DefaultModManager();
        }

        return instance;
    }

}
