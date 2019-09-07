package com.jukusoft.engine2d.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResourceUtils {

    //see also: https://stackoverflow.com/questions/3923129/get-a-list-of-resources-from-classpath-directory

    private ResourceUtils () {
        //
    }

    public static List<String> listResourceFiles(String path, Class<?> cls) throws IOException {
        List<String> filenames = new ArrayList<>();

        try (InputStream in = getResourceAsStream(path, cls); BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String fileName;

            while ((fileName = br.readLine()) != null) {
                filenames.add(fileName);
            }
        }

        return filenames;
    }

    private static InputStream getResourceAsStream(String resource, Class<?> cls) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? cls.getResourceAsStream(resource) : in;
    }

    private static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
