package com.jukusoft.engine2d.applayer.init.factory;

import com.jukusoft.engine2d.applayer.BaseApp;
import com.jukusoft.engine2d.applayer.init.impl.*;
import com.jukusoft.engine2d.applayer.plugin.PluginManager;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.applayer.init.InitializerProcessor;
import com.jukusoft.engine2d.core.version.Version;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InitializerProcessorFactory {

    private static List<Initializer> globalInitializers = new ArrayList<>();

    private InitializerProcessorFactory() {
        //
    }

    /**
     * create initializer processor and add default initializers
     *
     * @param gameClass game class (to get version information)
     */
    public static InitializerProcessor create(Class<?> gameClass, PluginManager pluginManager) {
        Objects.requireNonNull(gameClass);
        Objects.requireNonNull(pluginManager);
        List<Initializer> initializerList = new ArrayList<>();

        //add initializers before game engine init
        initializerList.add(new LogInitializer());
        initializerList.add(new VersionPrinterInitializer(new Version(gameClass)));
        initializerList.add(new ConfigInitializer());
        initializerList.add(new EventInitializer());
        initializerList.add(new TaskManagerInitializer());
        initializerList.addAll(globalInitializers);

        //add initializers which should be executed after splashscreen is shown
        initializerList.add(new DefaultConfigInitializer(BaseApp.class));
        initializerList.add(new GameConfigInitializer());
        initializerList.add(new PluginLoaderInitializer(pluginManager));

        return new InitializerProcessor(initializerList);
    }

    public static void addGlobalInitializer(Initializer initializer) {
        globalInitializers.add(initializer);
    }

}
