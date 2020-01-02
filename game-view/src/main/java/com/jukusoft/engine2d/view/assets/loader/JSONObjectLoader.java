package com.jukusoft.engine2d.view.assets.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import org.json.JSONObject;

public class JSONObjectLoader extends AsynchronousAssetLoader<JSONObject, JSONObjectLoader.JSONObjectParameter> {

    private JSONObject jsonObject;

    public JSONObjectLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, JSONObjectParameter parameter) {
        //parse json
        this.jsonObject = new JSONObject(file.readString("UTF-8"));
    }

    @Override
    public JSONObject loadSync(AssetManager manager, String fileName, FileHandle file, JSONObjectParameter parameter) {
        JSONObject jsonObject = this.jsonObject;
        this.jsonObject = null;
        return jsonObject;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, JSONObjectParameter parameter) {
        return null;
    }

    static public class JSONObjectParameter extends AssetLoaderParameters<JSONObject> {
    }

}
