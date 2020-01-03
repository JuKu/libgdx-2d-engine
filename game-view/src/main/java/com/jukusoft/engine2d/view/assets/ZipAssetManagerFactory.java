package com.jukusoft.engine2d.view.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.shutdown.ErrorHandler;
import com.jukusoft.engine2d.view.assets.loader.JSONArrayLoader;
import com.jukusoft.engine2d.view.assets.loader.JSONObjectLoader;
import com.jukusoft.engine2d.view.assets.zip.MultipleZipFileHandleResolver;
import com.jukusoft.engine2d.view.assets.zip.ZipFileHandleResolver;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mini2Dx.gdx.utils.Array;

import java.util.Iterator;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.zip.ZipFile;

public class ZipAssetManagerFactory {

    private ZipAssetManagerFactory() {
        //
    }

    /**
     * create a new asset manager instance which loads assets from a specific zip archive
     * <p>
     * Important! You have to cleanup later with assetManager.dispose()!
     *
     * @return
     */
    public static AssetManager create(ZipFile zipFile) {
        //see also: https://gist.github.com/MobiDevelop/5514357

        Objects.requireNonNull(zipFile);

        if (zipFile.size() == 0) {
            throw new IllegalStateException("zip file is empty");
        }

        ZipFileHandleResolver resolver = new ZipFileHandleResolver(zipFile);
        AssetManager assetManager = new AssetManager(resolver);

        assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(resolver));

        //set additional loader for json files
        assetManager.setLoader(JSONObject.class, new JSONObjectLoader(resolver));
        assetManager.setLoader(JSONArray.class, new JSONArrayLoader(resolver));

        //see also: https://github.com/libgdx/libgdx/wiki/Managing-your-assets
        //assetManager.setLoader(TiledMap.class, new TmxMapLoader(resolver));
        //assetManager.setLoader(Texture.class, new TextureLoader(resolver));

        return assetManager;
    }

    /**
     * create a new asset manager instance which loads assets from a specific zip archive
     * <p>
     * Important! You have to cleanup later with assetManager.dispose()!
     *
     * @return
     */
    public static AssetManager create(Array<ZipFile> zipFiles) {
        Objects.requireNonNull(zipFiles);

        if (zipFiles.size == 0) {
            throw new IllegalStateException("no zip files in list");
        }

        for (ZipFile zipFile : zipFiles) {
            if (zipFile.size() == 0) {
                throw new IllegalStateException("zip file is empty: " + zipFile.getName());
            }
        }

        MultipleZipFileHandleResolver resolver = new MultipleZipFileHandleResolver(zipFiles);
        AssetManager assetManager = new AssetManager(resolver);

        assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(resolver));

        //set additional loader for json files
        assetManager.setLoader(JSONObject.class, new JSONObjectLoader(resolver));
        assetManager.setLoader(JSONArray.class, new JSONArrayLoader(resolver));

        //find all asset loader implementation with SPI and register them
        Log.d(ZipAssetManagerFactory.class.getSimpleName(), "search for SPI asset loader classes...");
        ServiceLoader<SPIAssetLoader> loader = ServiceLoader.load(SPIAssetLoader.class);
        Iterator<SPIAssetLoader> iterator = loader.iterator();
        iterator.forEachRemaining(spiAssetLoader -> {
            Log.d(ZipAssetManagerFactory.class.getSimpleName(), "register SPI asset loader: " + spiAssetLoader.getClass().getCanonicalName());

            if (!(spiAssetLoader instanceof AssetLoader)) {
                Log.e(ZipAssetManagerFactory.class.getSimpleName(), "SPI asset loader is not an instance of libGDX AssetLoader: " + spiAssetLoader.getClass().getCanonicalName());
                ErrorHandler.shutdownWithException(new RuntimeException("SPI asset loader is not an instance of libGDX AssetLoader: " + spiAssetLoader.getClass().getCanonicalName()));

                return;
            }

            AssetLoader assetLoader = null;
            try {
                assetLoader = (AssetLoader) spiAssetLoader.getClass().getConstructor(FileHandleResolver.class).newInstance(resolver);
            } catch (Exception e) {
                e.printStackTrace();
                ErrorHandler.shutdownWithException(new RuntimeException("Cannot create instance of SPI asset loader: " + spiAssetLoader.getClass().getCanonicalName()));

                return;
            }

            assetManager.setLoader(spiAssetLoader.getLoadableAssetClass(), assetLoader);
        });

        //see also: https://github.com/libgdx/libgdx/wiki/Managing-your-assets
        //assetManager.setLoader(TiledMap.class, new TmxMapLoader(resolver));
        //assetManager.setLoader(Texture.class, new TextureLoader(resolver));

        return assetManager;
    }

}
