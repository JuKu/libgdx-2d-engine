package com.jukusoft.engine2d.view.assets;

/**
 * this interface is only used for SPI to find asset loaders and register them automatically to asset manager
 */
public interface SPIAssetLoader {

    /**
     * get asset class which assets can be loaded with this asset loader
     *
     * @return asset class which can be loaded with this loader
     */
    public Class<?> getLoadableAssetClass();

}
