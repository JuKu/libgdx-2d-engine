package com.jukusoft.engine2d.core.utils;

public class MathUtils {

    protected MathUtils() {
        //
    }

    public static float round3Digits(float x) {
        return (float) Math.floor(x * 1000.0f) / 1000.0f;
    }

    public static boolean overlapping(float minA, float maxA, float minB, float maxB) {
        return minB <= maxA && minA <= maxB;
    }

}
