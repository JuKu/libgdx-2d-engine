package com.jukusoft.engine2d.desktop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class SPIUtils {

    private SPIUtils () {
        //
    }

    public static <T> List<T> findImplementations (Class<T> cls) {
        ServiceLoader<T> loader = ServiceLoader .load(cls);

        //The search result is cached so we can invoke the ServiceLoader.reload() method in order to discover newly installed implementations
        loader.reload();

        List<T> list = new ArrayList<>();
        loader.forEach(factory -> list.add(factory));

        return list;
    }

}
