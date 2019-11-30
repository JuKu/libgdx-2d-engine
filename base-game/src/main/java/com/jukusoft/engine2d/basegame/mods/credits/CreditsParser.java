package com.jukusoft.engine2d.basegame.mods.credits;

import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreditsParser {

    public CreditsParser() {
        //
    }

    public List<CreditEntry> parse(String modName, InputStream is) {
        try (InputStreamReader isr = new InputStreamReader(is); BufferedReader reader = new BufferedReader(isr)) {
            String content = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            return parse(modName, content);
        } catch (IOException e) {
            Log.w(CreditsParser.class.getSimpleName(), "IOException while reading string from inputstream: ", e);
            return new ArrayList<>();
        }
    }

    public List<CreditEntry> parse(String modName, String content) {
        Objects.requireNonNull(modName);
        Objects.requireNonNull(content);

        if (content.isEmpty())
            throw new IllegalArgumentException("content cannot be empty");

        List<CreditEntry> list = new ArrayList<>();

        JSONArray array = new JSONArray(content);

        for (int i = 0; i < array.length(); i++) {
            JSONObject json = array.getJSONObject(i);
            checkJson(json);

            String path = json.getString("path");
            String title = json.getString("title");
            String author = json.getString("author");
            String url = json.getString("url");
            String license = json.getString("license");

            list.add(new CreditEntry(modName, path, title, author, url, license));
        }

        return list;
    }

    private void checkJson(JSONObject json) {
        if (!json.has("path")) {
            Log.w(CreditsParser.class.getSimpleName(), "path is missing in credits.json");
        }

        if (!json.has("title")) {
            Log.w(CreditsParser.class.getSimpleName(), "title is missing in credits.json");
        }

        if (!json.has("author")) {
            Log.w(CreditsParser.class.getSimpleName(), "author is missing in credits.json");
        }

        if (!json.has("license")) {
            Log.w(CreditsParser.class.getSimpleName(), "license is missing in credits.json");
        }
    }

}
