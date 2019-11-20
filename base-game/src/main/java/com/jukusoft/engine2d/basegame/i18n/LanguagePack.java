package com.jukusoft.engine2d.basegame.i18n;

public class LanguagePack {

    private final String token;
    private final String title;

    public LanguagePack(String token, String title) {
        this.token = token;
        this.title = title;
    }

    public String getToken() {
        return token;
    }

    public String getTitle() {
        return title;
    }

}
