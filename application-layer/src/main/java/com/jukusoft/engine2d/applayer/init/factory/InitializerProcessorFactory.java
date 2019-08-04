package com.jukusoft.engine2d.applayer.init.factory;

import com.jukusoft.engine2d.applayer.BaseApp;
import com.jukusoft.engine2d.applayer.init.Initializer;
import com.jukusoft.engine2d.applayer.init.InitializerProcessor;
import com.jukusoft.engine2d.applayer.init.impl.ConfigInitializer;
import com.jukusoft.engine2d.applayer.init.impl.DefaultConfigInitializer;
import com.jukusoft.engine2d.applayer.init.impl.LogInitializer;
import com.jukusoft.engine2d.applayer.init.impl.VersionPrinterInitializer;
import com.jukusoft.engine2d.core.version.Version;

import java.util.ArrayList;
import java.util.List;

public class InitializerProcessorFactory {

    private static List<Initializer> globalInitializers = new ArrayList<>();

    private InitializerProcessorFactory () {
        //
    }

    /**
    * create initializer processor and add default initializers
     *
     * @param gameClass game class (to get version information)
    */
    public static InitializerProcessor create (Class<?> gameClass) {
        List<Initializer> initializerList = new ArrayList<>();

        //add initializers before game engine init
        initializerList.add(new LogInitializer());
        initializerList.add(new VersionPrinterInitializer(new Version(gameClass)));
        initializerList.add(new ConfigInitializer());
        initializerList.addAll(globalInitializers);

        //add initializers which should be executed after splashscreen is shown
        initializerList.add(new DefaultConfigInitializer(BaseApp.class));

        return new InitializerProcessor(initializerList);
    }

    public static void addGlobalInitializer (Initializer initializer) {
        globalInitializers.add(initializer);
    }

}
