package com.jukusoft.engine2d.core.utils;

import com.jukusoft.engine2d.core.logger.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public static String getStringFromInputStream(InputStream is) {
        Objects.requireNonNull(is);

        try (InputStreamReader isr = new InputStreamReader(is); BufferedReader reader = new BufferedReader(isr)) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            Log.w(StringUtils.class.getSimpleName(), "IOException while reading string from inputstream: ", e);
            return "";
        }
    }

}
