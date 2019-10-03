package com.jukusoft.engine2d.basegame.mods.impl;

import com.jukusoft.engine2d.basegame.mods.ModLoader;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipModLoader implements ModLoader {

    private static final String LOG_TAG = "ModLoader";
    private Set<String> allowedExtensions = new HashSet<>();

    public ZipModLoader() {
        //get allowed mod extensions from config
        allowedExtensions = Arrays.stream(Config.get("Mods", "extensions").split(",")).collect(Collectors.toSet());

        if (allowedExtensions.size() == 0) {
            throw new IllegalStateException("no valid mod extension registered in mods config");
        }
    }

    @Override
    public List<Mod> findMods(File modDir) throws IOException {
        Objects.requireNonNull(modDir);

        if (!modDir.exists()) {
            throw new FileNotFoundException("mod dir does not exists: " + modDir.getAbsolutePath());
        }

        return Files.list(modDir.toPath())
                .filter(Files::isRegularFile)
                .filter(path -> {
                    for (String extension : allowedExtensions) {
                        if (path.getFileName().toString().endsWith(extension)) {
                            return true;
                        }
                    }

                    return false;
                })
                .map(path -> path.toFile())
                .map(this::parseMod)
                .filter(mod -> mod != null)
                .collect(Collectors.toList());
    }

    private Mod parseMod(File modZip) {
        if (!modZip.exists()) {
            Log.w(LOG_TAG, "mod does not exists: " + modZip.getAbsolutePath());
            return null;
        }

        try {
            ZipFile zipFile = new ZipFile(modZip);
            ZipEntry modJsonEntry = zipFile.getEntry("mod.json");

            if (modJsonEntry == null) {
                Log.w(LOG_TAG, "invalid mod zip file, zip does not contains mod.json: " + modZip.getAbsolutePath());
                return null;
            }

            String content = FileUtils.getContentFromInputStream(zipFile.getInputStream(modJsonEntry), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(content);

            if (!validateJson(json)) {
                Log.w(LOG_TAG, "invalid mod json, json does not contains all required fields: " + modZip.getAbsolutePath());
                return null;
            }

            JSONArray typesArray = json.getJSONArray("types");
            Set<String> types = typesArray.toList().stream().map(str -> (String) str).collect(Collectors.toSet());

            Mod mod = new Mod(
                    json.getString("name"),
                    json.getString("title"),
                    json.getString("description"),
                    types,
                    json.getString("version")
            );

            if (json.has("url")) {
                mod.setUrl(json.getString("url"));
            }

            if (json.has("dependencies")) {
                JSONArray array = json.getJSONArray("dependencies");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject dependency = array.getJSONObject(i);

                    for (String key : dependency.keySet()) {
                        String value = dependency.getString(key);
                        mod.addDependency(key, value);
                    }
                }
            }

            return mod;
        } catch (IOException e) {
            Log.w(LOG_TAG, "Cannot parse mod zip file: " + modZip.getAbsolutePath() + " caused by IOException: ", e);
            return null;
        }
    }

    private boolean validateJson(JSONObject json) {
        return json.has("name") && json.has("title") && json.has("description") && json.has("type") && json.has("version");
    }

}
