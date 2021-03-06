package com.jukusoft.engine2d.core.config;

import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Cache {

    protected static Cache instance = null;

    protected final String CACHE_PATH;

    protected Cache(String path) {
        Objects.requireNonNull(path, "path cannot be null.");

        if (!new File(path).exists()) {
            throw new IllegalStateException("cache directory doesn't exists: " + new File(path).getAbsolutePath());
        }

        if (!new File(path).isDirectory()) {
            throw new IllegalStateException("cache directory isn't a directory: " + new File(path).getAbsolutePath());
        }

        if (!path.endsWith("/")) {
            path += "/";
        }

        Log.d("Cache", "used cache directory: " + path);

        CACHE_PATH = path;

        //create directory, if neccessary
        this.createRequiredDirs();
    }

    protected void createRequiredDirs() {
        FileUtils.createWritableDirIfAbsent(CACHE_PATH);
        FileUtils.createWritableDirIfAbsent(CACHE_PATH + "assets/");
        FileUtils.createWritableDirIfAbsent(CACHE_PATH + "security/");
        FileUtils.createWritableDirIfAbsent(CACHE_PATH + "maps/");
    }

    /**
     * initialize cache
     *
     * @param path cache directory path
     */
    public static void init(String path) {
        instance = new Cache(path);
    }

    public static Cache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You havent initialized cache with Cache::init() before.");
        }

        return instance;
    }

    public String getPath() {
        return CACHE_PATH;
    }

    public void createDirIfAbsent(String dirName) {
        FileUtils.createWritableDirIfAbsent(CACHE_PATH + dirName + "/");
    }

    public String getCachePath(String dirName) {
        return CACHE_PATH + dirName + "/";
    }

    public String getCacheFilePath(String filePath) {
        return CACHE_PATH + filePath;
    }

    public void clear() throws IOException {
        FileUtils.recursiveDeleteDirectory(new File(CACHE_PATH), false);
        createRequiredDirs();
    }

}
