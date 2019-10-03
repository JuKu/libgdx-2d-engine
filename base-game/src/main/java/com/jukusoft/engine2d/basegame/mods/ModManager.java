package com.jukusoft.engine2d.basegame.mods;

import com.jukusoft.engine2d.basegame.mods.impl.DefaultModManager;
import com.jukusoft.engine2d.basegame.mods.impl.Mod;
import org.mini2Dx.gdx.utils.Array;

import java.io.File;

public interface ModManager {

    public void loadFromDir(File modDir);

    public Array<Mod> listMods();

    public static ModManager getInstance() {
        return DefaultModManager.getInstance();
    }

}
