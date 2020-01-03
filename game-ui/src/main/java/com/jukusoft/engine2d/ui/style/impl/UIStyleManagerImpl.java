package com.jukusoft.engine2d.ui.style.impl;

import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.basegame.mods.ModManager;
import com.jukusoft.engine2d.basegame.mods.impl.Mod;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.StringUtils;
import com.jukusoft.engine2d.ui.style.UIStyle;
import com.jukusoft.engine2d.ui.style.UIStyleManager;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class UIStyleManagerImpl implements UIStyleManager {

    private static UIStyleManagerImpl instance;

    private String currentStylePath = Config.get("UIStyles", "defaultStyle");

    //available style paths in asset packs
    Array<String> stylePaths = null;

    private UIStyle currentStyle;
    private boolean loaded = false;

    private UIStyleManagerImpl() {
        //find all available ui styles
        this.stylePaths = listStylePaths();
        Log.i(UIStyleManagerImpl.class.getSimpleName(), "" + stylePaths.size + " styles found in asset packs");
    }

    @Override
    public String getCurrentStylePath() {
        return currentStylePath;
    }

    @Override
    public void setCurrentStylePath(String stylePath) {
        this.currentStylePath = stylePath;
    }

    @Override
    public boolean isStyleAvailable(String stylePath) {
        return stylePaths.contains(stylePath, false);
    }

    @Override
    public void load() {
        //check, if current style is available
        if (!stylePaths.contains(currentStylePath, false)) {
            throw new IllegalStateException("selected ui style is not available in asset packs: " + currentStylePath);
        }

        Log.d(UIStyleManagerImpl.class.getSimpleName(), "found current ui style in asset packs: " + currentStylePath);

        GameAssetManager assetManager = GameAssetManager.getInstance();

        //load current style
        assetManager.load(currentStylePath, JSONObject.class);
        assetManager.finishLoading(currentStylePath);
        JSONObject json = assetManager.get(currentStylePath);
        this.currentStyle = new UIStyle(json);

        //call asset manager to load ui style

        //TODO: add code here

        loaded = true;
    }

    @Override
    public void unload() {
        if (!loaded) {
            return;
        }

        if (currentStyle != null) {
            currentStyle.unload(GameAssetManager.getInstance());
        }

        //TODO: unload assets

        loaded = false;
    }

    @Override
    public UIStyle getCurrentStyle() {
        if (currentStyle == null) {
            throw new IllegalStateException("method load() was not called before");
        }

        return currentStyle;
    }

    @Override
    public Array<UIStyle> listAllStyles() {
        return null;
    }

    @Override
    public void reload() {
        unload();
        load();
    }

    private Array<String> listStylePaths() {
        Log.d(UIStyleManagerImpl.class.getSimpleName(), "listStylePaths()");

        Array<String> stylePaths = new Array<>();

        String listFileName = Config.get("UIStyles", "stylesName");

        //search for available styles in mods
        for (Mod mod : ModManager.getInstance().findModsWithSpecificFile(listFileName)) {
            Log.d(UIStyleManagerImpl.class.getSimpleName(), "find mod: " + mod.getArchiveFile());

            try (ZipFile zipFile = new ZipFile(mod.getArchiveFile())) {
                ZipEntry zipEntry = zipFile.getEntry(listFileName);

                if (zipEntry == null) {
                    Log.w(UIStyleManagerImpl.class.getSimpleName(), "zip file does not contain file '" + listFileName + "': " + mod.getArchiveFile().getAbsolutePath());
                    continue;
                }

                String jsonString = StringUtils.getStringFromInputStream(zipFile.getInputStream(zipEntry));
                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.d(UIStyleManagerImpl.class.getSimpleName(), "style found in asset packs: " + jsonArray.getString(i));
                    stylePaths.add(jsonArray.getString(i));
                }
            } catch (ZipException e) {
                e.printStackTrace();
                Log.w(UIStyleManagerImpl.class.getSimpleName(), "ZipException while trying to open mod zip file: " + mod.getArchiveFile());
            } catch (IOException e) {
                e.printStackTrace();
                Log.w(UIStyleManagerImpl.class.getSimpleName(), "IOException while trying to open mod zip file: " + mod.getArchiveFile());
            }
        }

        if (stylePaths.size == 0) {
            throw new IllegalStateException("no ui styles found in mods (asset filePath: " + listFileName + ")");
        }

        return stylePaths;
    }

    public static UIStyleManager getInstance() {
        if (instance == null) {
            instance = new UIStyleManagerImpl();
        }

        return instance;
    }

}
