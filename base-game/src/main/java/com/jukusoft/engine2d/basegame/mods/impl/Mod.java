package com.jukusoft.engine2d.basegame.mods.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Mod {

    public enum Type {
        MOD,
        DLC,
        PATCH,
        GAMEPACK,
        ASSETPACK
    }

    private final String name;
    private final String title;
    private final String description;
    private final Set<Type> types;
    private final String version;
    private String url;
    private Map<String, String> dependencies = new HashMap<>();

    protected Mod(String name, String title, String description, Set<String> typesString, String version) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.types = typesString.stream().map(type -> Type.valueOf(type.replace("_", "").toUpperCase())).collect(Collectors.toSet());
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

    public Set<Type> getTypes() {
        return types;
    }

    public boolean hasType(Type type) {
        return types.contains(type);
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
