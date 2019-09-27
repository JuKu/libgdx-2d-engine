package com.jukusoft.engine2d.basegame.mods;

import java.io.File;
import java.util.List;

public interface ModLoader {

    public List<Mod> findMods(File modDir);

}
