package com.jukusoft.engine2d.ui.style;

import com.badlogic.gdx.files.FileHandle;
import com.jukusoft.engine2d.core.utils.StringUtils;
import com.jukusoft.engine2d.ui.Widget;
import org.json.JSONObject;

import java.util.Objects;

public class UIStyle {

    private JSONObject json;

    public UIStyle(FileHandle handle) {
        this(handle.readString("UTF-8"));
    }

    public UIStyle(String content) {
        this(new JSONObject(content));
    }

    public UIStyle(JSONObject json) {
        Objects.requireNonNull(json);
        this.json = json;
    }

    public JSONObject getWidgetStyle(Class<? extends Widget> cls) {
        String key = cls.getCanonicalName();
        String className = cls.getSimpleName();

        if (json.has(key)) {
            return json.getJSONObject(key);
        } else if (json.has(className)) {
            return json.getJSONObject(className);
        }

        return null;
    }

}
