package com.jukusoft.engine2d.core.utils;

import java.util.Objects;

public class StringUtils {

    private StringUtils() {
        //
    }

    public static void checkNotNullAndNotEmpty(String str) {
        checkNotNullAndNotEmpty(str, "string");
    }

    public static void checkNotNullAndNotEmpty(String str, String messageObj) {
        Objects.requireNonNull(str);

        if (str.isEmpty()) throw new IllegalArgumentException(messageObj + " cannot be null");
    }

}
