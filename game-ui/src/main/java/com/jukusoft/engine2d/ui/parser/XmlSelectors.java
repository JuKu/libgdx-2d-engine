package com.jukusoft.engine2d.ui.parser;

public enum XmlSelectors {

    ROOT("//root"),
    SCREEN("//root/screen");

    private String xpath;

    XmlSelectors(final String xpath) {
        this.xpath = xpath;
    }

    public String getXPath() {
        return xpath;
    }

    @Override
    public String toString() {
        return getXPath();
    }

}
