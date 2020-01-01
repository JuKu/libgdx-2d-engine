package com.jukusoft.engine2d.ui.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.ui.UIScreen;
import com.jukusoft.engine2d.ui.parser.UIXMLParser;
import com.jukusoft.engine2d.ui.parser.UIXMLParserImpl;
import com.jukusoft.engine2d.view.assets.SPIAssetLoader;

import java.io.IOException;

public class UIScreenLoader extends AsynchronousAssetLoader<UIScreen, UIScreenLoader.UIScreenParameter> implements SPIAssetLoader {

    private UIScreen uiScreen;

    /**
     * public constructor is required for SPI
     */
    public UIScreenLoader() {
        super(null);
    }

    public UIScreenLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    /** Returns the {@link UIScreen} instance currently loaded by this
     * {@link UIScreenLoader}.
     *
     * @return the currently loaded {@link UIScreen}, otherwise {@code null} if
     *         no {@link UIScreen} has been loaded yet. */
    protected UIScreen getLoadedUIScreen () {
        return uiScreen;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, UIScreenParameter parameter) {
        //parse ui screen file
        UIXMLParser parser = new UIXMLParserImpl();

        try {
            uiScreen = parser.parseScreen(file);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(UIScreenLoader.class.getSimpleName(), "IOException while loading ui screen: " + file.path(), e);
            manager.getLogger().error("IOException while loading ui screen <" + fileName + "> :\n" + e.getLocalizedMessage());
        }
    }

    @Override
    public UIScreen loadSync(AssetManager manager, String fileName, FileHandle file, UIScreenParameter parameter) {
        UIScreen uiScreen = this.uiScreen;
        this.uiScreen = null;
        return uiScreen;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, UIScreenParameter parameter) {
        Array<AssetDescriptor> dependencies = new Array();

        //see also: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/assets/loaders/TextureAtlasLoader.java

        return dependencies;
    }

    @Override
    public Class<?> getLoadableAssetClass() {
        return UIScreen.class;
    }

    static public class UIScreenParameter extends AssetLoaderParameters<UIScreen> {
    }

}
