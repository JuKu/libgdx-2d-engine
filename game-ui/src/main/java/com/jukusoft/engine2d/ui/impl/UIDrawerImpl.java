package com.jukusoft.engine2d.ui.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.sound.controller.SoundEngineController;
import com.jukusoft.engine2d.ui.UIDrawer;
import com.jukusoft.engine2d.ui.UIScreen;
import com.jukusoft.engine2d.ui.Widget;
import com.jukusoft.engine2d.ui.dto.Soundtrack;
import com.jukusoft.engine2d.ui.style.StyleManager;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;

import java.net.URI;
import java.util.Objects;

public class UIDrawerImpl extends InputAdapter implements UIDrawer {

    private String uiScreenXMLPath;
    private String oldUiScreenXMLPath;
    private UIScreen screen;
    private boolean debugMode = false;

    private OrthographicCamera camera;
    private Viewport viewport;

    private final SpriteBatch batch;
    private boolean ownSpriteBatch = false;

    private Texture backgroundTexture;

    private StyleManager styleManager;

    public UIDrawerImpl() {
        this(new SpriteBatch());
        ownSpriteBatch = true;
    }

    public UIDrawerImpl(SpriteBatch batch) {
        camera = new OrthographicCamera();

        viewport = new ScreenViewport(camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        this.batch = batch;
    }

    @Override
    public void load(String xmlPath) {
        this.oldUiScreenXMLPath = this.uiScreenXMLPath;
        this.uiScreenXMLPath = xmlPath;
        reload();
    }

    protected void loadAssets() {
        GameAssetManager assetManager = GameAssetManager.getInstance();

        //load background, if neccessary
        if (!screen.getBackground().isEmpty()) {
            assetManager.load(screen.getBackground(), Texture.class);
            assetManager.finishLoading(screen.getBackground());
            this.backgroundTexture = assetManager.get(screen.getBackground());
        }
    }

    protected void unloadAssets() {
        //unload background, if neccessary
        if (!screen.getBackground().isEmpty()) {
            GameAssetManager.getInstance().unload(screen.getBackground());
        }
    }

    public int getWidth() {
        return Gdx.graphics.getWidth();
    }

    public int getHeight() {
        return Gdx.graphics.getHeight();
    }

    @Override
    public void reload() {
        Log.d(UIDrawerImpl.class.getSimpleName(), "reload ui screen");

        GameAssetManager assetManager = GameAssetManager.getInstance();

        //dispose old screen and unload assets, if neccessary
        if (screen != null) {
            screen.dispose(styleManager, assetManager);

            unloadAssets();
        }

        //unload old screen
        if (assetManager.isLoaded(oldUiScreenXMLPath)) {
            assetManager.unload(oldUiScreenXMLPath);
        }

        //load screen from xml
        assetManager.load(uiScreenXMLPath, UIScreen.class);
        assetManager.finishLoading(uiScreenXMLPath);
        this.screen = assetManager.get(uiScreenXMLPath);
        Objects.requireNonNull(this.screen);

        //initialize screen
        screen.init(styleManager, assetManager);

        //load assets
        loadAssets();

        //set soundtrack
        if (!screen.listSoundtracks().isEmpty()) {
            //get random soundtrack
            Soundtrack soundtrack = screen.listSoundtracks().random();
            Log.d(UIDrawerImpl.class.getSimpleName(), "set soundtrack: " + soundtrack.getPath());

            SoundEngineController.playBackgroundSoundtrack(soundtrack.getPath(), soundtrack.isLoop(), soundtrack.getVolume());

            //TODO: set all soundtracks to sound engine to play
        }
    }

    @Override
    public void update(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //update all widgets
        for (Widget widget : screen.listWidgets()) {
            if (widget != null) {
                widget.update(delta, 0, 0);
            }
        }
    }

    @Override
    public void draw() {
        if (ownSpriteBatch) {
            batch.begin();
        }

        //draw background, if available
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, getWidth(), getHeight());
        }

        //draw all widgets
        for (Widget widget : screen.listWidgets()) {
            if (widget != null && widget.isVisible()) {
                widget.draw(batch, 0, 0);
            }
        }

        if (ownSpriteBatch) {
            batch.end();
        }
    }

    @Override
    public <T extends Widget> T findWidgetbyId(String id, Class<T> cls) {
        return null;
    }

    @Override
    public void setDebug(boolean debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public boolean isDebug() {
        return this.debugMode;
    }

    @Override
    public void addFeatureTag(String featureTag) {

    }

    @Override
    public void removeFeatureTag(String featureTag) {

    }

    @Override
    public boolean hasFeatureTag(String featureTag) {
        return false;
    }

    @Override
    public void onResize(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        viewport.update(newWidth, newHeight, true);
    }

    @Override
    public boolean keyDown(int keycode) {
        return screen.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (debugMode) {
            //refresh screen on F5
            if (keycode == Input.Keys.F5) {
                Log.d(UIDrawerImpl.class.getSimpleName(), "refresh screen now");
                reload();
            }
        }

        return screen.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return screen.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return screen.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return screen.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return screen.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return screen.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return screen.scrolled(amount);
    }
}
