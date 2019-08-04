package com.jukusoft.engine2d.applayer.init;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class SplashScreenDrawer implements Disposable {

    private SpriteBatch batch;
    private Texture engineSplashScreen;

    public SplashScreenDrawer() {
        batch = new SpriteBatch();

        //set width & height
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.engineSplashScreen = new Texture(Gdx.files.internal("assets/engine_splashscreen.png"));
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(this.engineSplashScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void dispose() {
        this.engineSplashScreen.dispose();
        this.engineSplashScreen = null;
        this.batch.dispose();
        this.batch = null;
    }

}
