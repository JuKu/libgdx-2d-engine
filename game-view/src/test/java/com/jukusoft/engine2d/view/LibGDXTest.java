package com.jukusoft.engine2d.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import com.badlogic.gdx.backends.headless.HeadlessNet;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.graphics.GL20;
import org.junit.BeforeClass;

import static org.mockito.Mockito.mock;

public class LibGDXTest {

    @BeforeClass
    public static void beforeClass() {
        System.err.println("initialize headless libgdx instance");
        HeadlessNativesLoader.load();
        MockGraphics mockGraphics = new MockGraphics();
        Gdx.graphics = mockGraphics;
        HeadlessFiles headlessFiles = new HeadlessFiles();
        Gdx.files = headlessFiles;
        Gdx.gl = mock(GL20.class);
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessNet headlessNet = new HeadlessNet(config);
        Gdx.net = headlessNet;
        //ApplicationListener myGdxGame = EntryPoint.getHeadlessMyGdxGame(config);
    }

}
