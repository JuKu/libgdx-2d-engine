package com.jukusoft.engine2d.view.assets.assetmanager;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.utils.Array;

public interface GameAssetManager {

    static final GameAssetManager instance = new GameAssetManagerImpl();

    public void update();

    /**
     * load asset
     *
     * @param asset asset
     */
    public void load(AssetInfo asset);

    /**
     * load asset from path
     *
     * @param path relative asset path (in zip file)
     * @param cls  type of asset
     */
    public void load(String path, Class<?> cls);

    /**
     * Adds the given asset to the loading queue of the AssetManager.
     *
     * @param path      relative asset path (in zip file)
     * @param type      the type of the asset
     * @param parameter parameters for the SPIAssetLoader
     */
    public <T> void load(String path, Class<T> type, AssetLoaderParameters<T> parameter);

    /**
     * cleanup memory for asset
     */
    public void unload(AssetInfo asset);

    public void unload(String path);

    /**
     * get instance of loaded asset
     *
     * @param fileName path to asset file
     */
    public <T> T get(String fileName);

    /**
     * get instance of loaded asset
     *
     * @param fileName path to asset file
     * @param type     asset type
     */
    public <T> T get(String fileName, Class<T> type);

    /**
     * check, if asset was loaded
     *
     * @param filePath path to asset file
     */
    public boolean isLoaded(String filePath);

    /**
     * get instance of loaded asset
     *
     * @param asset asset info
     */
    public <T> T get(AssetInfo asset);

    /**
     * store an loaded asset to an specific name
     *
     * @param name  unique asset name
     * @param asset loaded instance of asset
     */
    public <T> void addAssetByName(String name, T asset);

    public void removeAssetName(String name);

    public <T> T getAssetByName(String name, Class<T> cls);

    public void finishLoading(String fileName);

    public void finishLoading();

    public float getProgress();

    public Array<String> listLoadedAssets();

    public void dispose();

    public static GameAssetManager getInstance() {
        return instance;
    }

}
