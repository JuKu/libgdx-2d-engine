package com.jukusoft.engine2d.basegame.mods.impl;

import java.util.HashMap;
import java.util.Map;

public class Mod {

    public enum Type {
        MOD,
        DLC,
        PATCH,
        GAMEPACK
    }

    private final String name;
    private final String title;
    private final String description;
    private final Type type;
    private final String version;
    private String url;
    private Map<String,String> dependencies = new HashMap<>();

    protected Mod(String name, String title, String description, String type, String version) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.type = Type.valueOf(type.toUpperCase());
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> listDependencies() {
        return dependencies;
    }

    public void addDependency(String dependencyName, String version) {
        dependencies.put(dependencyName, version);
    }

}
