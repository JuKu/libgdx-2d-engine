package com.jukusoft.engine2d.view.assets.zip;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

import java.util.zip.ZipFile;

//see also: https://gist.github.com/MobiDevelop/5514357
public class ZipFileHandleResolver implements FileHandleResolver {

    private final ZipFile zipFile;

    public ZipFileHandleResolver(ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    @Override
    public FileHandle resolve(String fileName) {
        return new ArchiveFileHandle(zipFile, fileName);
    }

}
