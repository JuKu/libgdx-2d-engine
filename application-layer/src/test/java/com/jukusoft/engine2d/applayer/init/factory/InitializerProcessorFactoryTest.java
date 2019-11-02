package com.jukusoft.engine2d.applayer.init.factory;

import com.jukusoft.engine2d.applayer.init.InitializerProcessor;
import com.jukusoft.engine2d.applayer.plugin.PluginManager;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.init.Initializer;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class InitializerProcessorFactoryTest {

    @Test(expected = NullPointerException.class)
    public void testAddGlobalNullInitializer() {
        InitializerProcessorFactory.addGlobalInitializer(null);
    }

    @Test
    public void testAddGlobalInitializer() {
        InitializerProcessorFactory.addGlobalInitializer(() -> {
            //
        });
    }

    @Test
    public void testCreate() {
        InitializerProcessor initializerProcessor = InitializerProcessorFactory.create(Game.class, Mockito.mock(PluginManager.class));
        assertNotNull(initializerProcessor);
        assertFalse(initializerProcessor.hasFinished());
    }

}
