package com.jukusoft.engine2d.basegame.mods;

import com.jukusoft.engine2d.basegame.mods.impl.Mod;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ModLoader {

    public List<Mod> findMods(File modDir) throws IOException;

}
