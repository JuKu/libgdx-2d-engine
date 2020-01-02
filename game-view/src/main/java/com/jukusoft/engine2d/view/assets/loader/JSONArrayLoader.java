package com.jukusoft.engine2d.view.assets.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONArrayLoader extends AsynchronousAssetLoader<JSONArray, JSONArrayLoader.JSONArrayParameter> {

    private JSONArray jsonArray;

    public JSONArrayLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, JSONArrayParameter parameter) {
        //parse json
        this.jsonArray = new JSONArray(file.readString("UTF-8"));
    }

    @Override
    public JSONArray loadSync(AssetManager manager, String fileName, FileHandle file, JSONArrayParameter parameter) {
        JSONArray jsonArray = this.jsonArray;
        this.jsonArray = null;
        return jsonArray;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, JSONArrayParameter parameter) {
        return null;
    }

    static public class JSONArrayParameter extends AssetLoaderParameters<JSONArray> {
    }

}
