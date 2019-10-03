package com.jukusoft.engine2d.basegame.mods;

import com.jukusoft.engine2d.basegame.mods.impl.DefaultModManager;
import com.jukusoft.engine2d.basegame.mods.impl.Mod;
import org.mini2Dx.gdx.utils.Array;

import java.io.File;

public interface ModManager {

    public void loadFromDir(File modDir);

    /**
     * list all loaded mods
     *
     * @return list with all loaded mods
     */
    public Array<Mod> listMods();

    /**
     * list all mods which belongs to this mod type
     * Attention! This method will cause GC pressure!
     *
     * @param type mod type to filter
     * @return list with mods which belongs to this mod type
     */
    public Array<Mod> listMods(Mod.Type type);

    public static ModManager getInstance() {
        return DefaultModManager.getInstance();
    }

}
