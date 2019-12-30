package com.jukusoft.engine2d.ui.dto;

import com.jukusoft.engine2d.core.utils.StringUtils;

public class Soundtrack {

    private final String path;
    private final boolean loop;
    private final float volume;

    public Soundtrack(String path, boolean loop, float volume) {
        StringUtils.checkNotNullAndNotEmpty(path, "path");

        if (volume < 0) {
            throw new IllegalArgumentException("volume cannot be < 0");
        }

        if (volume > 1.0) {
            throw new IllegalArgumentException("volume cannot be > 1");
        }

        this.path = path;
        this.loop = loop;
        this.volume = volume;
    }

    public String getPath() {
        return path;
    }

    public boolean isLoop() {
        return loop;
    }

    public float getVolume() {
        return volume;
    }

}
