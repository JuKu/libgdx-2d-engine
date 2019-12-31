package com.jukusoft.engine2d.ui.utils;

import net.sf.saxon.s9api.XdmItem;

import java.util.Objects;

public class XMLUtils {

    private XMLUtils() {
        //
    }

    /**
     * get string value
     *
     * @param item xml item
     * @param objName object name for correct exception message
     *
     * @return item content
     */
    public static String getString(XdmItem item, String objName) {
        if (item == null) {
            throw new IllegalStateException("xml content or attribute '" + objName + "' is not set, item is null");
        }

        if (item.getStringValue().isEmpty()) {
            throw new IllegalStateException("xml content or attribute '" + objName + "' is empty, item is null");
        }

        return item.getStringValue();
    }

    public static String getStringOrDefault(XdmItem item, String defaultStr) {
        return item != null ? item.getStringValue() : defaultStr;
    }

    public static int getInt(XdmItem item, String objName) {
        return Integer.parseInt(getString(item, objName));
    }

    public static int getIntOrDefault(XdmItem item, int defValue) {
        return Integer.parseInt(getStringOrDefault(item, Integer.toString(defValue)));
    }

    public static float getFloat(XdmItem item, String objName) {
        return Float.parseFloat(getString(item, objName));
    }

    public static float getFloatOrDefault(XdmItem item, float defValue) {
        if (item == null || item.getStringValue().isEmpty()) {
            return defValue;
        }

        return Float.parseFloat(getString(item, "xml tag or attribute"));
    }

}
