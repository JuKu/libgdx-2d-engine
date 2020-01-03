package com.jukusoft.engine2d.ui.widgets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.engine2d.ui.WidgetAdapter;
import com.jukusoft.engine2d.ui.style.UIStyleManager;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;
import org.json.JSONObject;

import java.util.Objects;

public class Button extends WidgetAdapter {

    @Override
    protected void initWidget(UIStyleManager styleManager, GameAssetManager assetManager) {
        Objects.requireNonNull(styleManager);
        Objects.requireNonNull(assetManager);

        //get button style
        JSONObject buttonStyle = styleManager.getCurrentStyle().getWidgetStyle(Button.class);

        //TODO: add code here
    }

    @Override
    protected void dispose(UIStyleManager styleManager, GameAssetManager assetManager) {
        //
    }

    @Override
    public void update(float delta, float offsetX, float offsetY) {

    }

    @Override
    public void draw(SpriteBatch batch, float offsetX, float offsetY) {

    }

}
