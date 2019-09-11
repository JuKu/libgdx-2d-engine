package com.jukusoft.engine2d.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceUtils {

    //see also: https://stackoverflow.com/questions/3923129/get-a-list-of-resources-from-classpath-directory

    private ResourceUtils() {
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

    /*public static String getResourceFileContent (String filePath, Class<?> cls) throws URISyntaxException, IOException {
        URL url = cls.getResource(filePath);

        if (url == null) {
            throw new FileNotFoundException("resource file does not exists in classpath: " + filePath);
        }

        return new String(Files.readAllBytes(Paths.get(cls.getResource(filePath).toURI())));
    }*/

    /**
     * Reads given resource file as a string.
     * <p>
     * see also: https://stackoverflow.com/questions/6068197/utils-to-read-resource-text-file-to-string-java
     *
     * @param fileName path to the resource file
     * @return the file's contents
     * @throws IOException if read fails for any reason
     */
    public static String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;

            try (InputStreamReader isr = new InputStreamReader(is); BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

}
