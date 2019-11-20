package com.jukusoft.engine2d.basegame.i18n;

import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.FilePath;
import com.jukusoft.engine2d.core.utils.FileUtils;
import com.jukusoft.i18n.I;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class I18NHelper {

    private static final String SECTION_NAME = "I18N";
    private static final String LOG_TAG = I18NHelper.class.getSimpleName();

    private I18NHelper() {
        //
    }

    public static List<LanguagePack> listAvailableLanguagePacks() {
        //get i18n directory
        File i18nDir = getI18NDir();

        try {
            return Files.list(i18nDir.toPath())
                    .filter(Files::isDirectory)
                    .filter(dirPath -> {
                        File jsonFile = new File(dirPath.toFile(), "lang.json");
                        return jsonFile.exists();
                    })
                    .map(dirPath -> new File(dirPath.toFile(), "lang.json"))
                    .map(file -> {
                        try {
                            String content = FileUtils.readFile(file.getAbsolutePath(), StandardCharsets.UTF_8);
                            return new JSONObject(content);
                        } catch (IOException e) {
                            Log.w(SECTION_NAME, "Cannot parse lang.json: " + file.getAbsolutePath(), e);
                            return null;
                        }
                    })
                    .filter(json -> json.has("token") && json.has("title"))
                    .map(json -> new LanguagePack(json.getString("token"), json.getString("title")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            Log.e(SECTION_NAME, "IOException while list available language packs: ", e);
            return new ArrayList<>();
        }
    }

    public static String getI18NDirPath() {
        return Config.get(SECTION_NAME, "dir");
    }

    public static File getI18NDir() {
        String dir = getI18NDirPath();
        File i18nFolder = new File(FilePath.parse(dir));

        return i18nFolder;
    }

    public static String getCurrentLanguageToken() {
        return Config.get(SECTION_NAME, "token");
    }

    public static void setLanguage(String token) {
        Objects.requireNonNull(token);

        if (token.isEmpty()) {
            throw new IllegalArgumentException("token cannot be empty");
        }

        //check, if language is available
        if (!listAvailableLanguagePacks().stream().map(langPack -> langPack.getToken()).anyMatch(langToken -> langToken.equals(token))) {
            throw new IllegalStateException("language '" + token + "' is not available!");
        }

        Config.set(SECTION_NAME, "token", token);

        //TODO: persist config

        Log.i(LOG_TAG, "select language: " + token);
        I.setLanguage(token);
    }

}
