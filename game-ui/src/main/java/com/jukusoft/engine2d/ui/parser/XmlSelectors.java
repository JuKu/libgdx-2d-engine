package com.jukusoft.engine2d.ui.parser;

public enum XmlSelectors {

    ROOT("//root"),
    SCREEN("//root/screen"),

    SCREEN_ID("string(@id)"),
    SCREEN_BACKGROUND("string(@background)"),

    PROPERTIES("properties"),
    PROPERTY_XPOS("xPos"),
    PROPERTY_YPOS("yPos"),
    PROPERTY_WIDTH("width"),
    PROPERTY_HEIGHT("height"),

    STYLE("styles/style"),

    SOUNDTRACK("music/soundtrack"),

    ATTR_PATH("string(@path)"),
    ATTR_LOOP("string(@loop)"),
    ATTR_VOLUME("string(@volume)"),

    CONTAINER("container"),

    BUTTON("button"),
    CUSTOM_WIDGETS("widget"),
    CUSTOM_WIDGETS_CLASS("string(@type)"),

    FEATURE_TAGS("string(@featuretags)"),

    WIDGET_ID("string(@id)"),

    POS_X("string(@x)"),
    POS_Y("string(@y)"),
    POS_Z("string(@z)"),

    WIDTH("string(@width)"),
    HEIGHT("string(@height)"),
    ;

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
