package com.jukusoft.engine2d.view.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.jukusoft.engine2d.view.assets.zip.ZipFileHandleResolver;

import java.util.Objects;
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

        //see also: https://github.com/libgdx/libgdx/wiki/Managing-your-assets
        //assetManager.setLoader(TiledMap.class, new TmxMapLoader(resolver));
        //assetManager.setLoader(Texture.class, new TextureLoader(resolver));

        return assetManager;
    }

}
