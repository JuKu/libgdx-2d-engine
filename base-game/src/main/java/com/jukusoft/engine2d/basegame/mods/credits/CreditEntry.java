package com.jukusoft.engine2d.basegame.mods.credits;

public class CreditEntry implements Comparable<CreditEntry> {

    private final String modName;
    private final String path;
    private final String title;
    private final String author;
    private final String url;
    private final String license;

    public CreditEntry(String modName, String path, String title, String author, String url, String license) {
        this.modName = modName;
        this.path = path;
        this.title = title;
        this.author = author;
        this.url = url;
        this.license = license;
    }

    public String getModName() {
        return modName;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getLicense() {
        return license;
    }

    @Override
    public int compareTo(CreditEntry o) {
        return o.getAuthor().compareTo(this.getAuthor());
    }

}
