package com.jukusoft.engine2d.view.assets.assetmanager;

import com.jukusoft.engine2d.core.utils.StringUtils;

public class AssetNotLoadedException extends RuntimeException {

    public AssetNotLoadedException(String message) {
        super(message);
        StringUtils.checkNotNullAndNotEmpty(message);
    }

}
