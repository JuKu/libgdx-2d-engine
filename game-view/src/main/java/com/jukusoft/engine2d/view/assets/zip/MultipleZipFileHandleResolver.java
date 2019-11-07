package com.jukusoft.engine2d.view.assets.zip;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.mini2Dx.gdx.utils.Array;

import java.util.Objects;
import java.util.zip.ZipFile;

//see also: https://gist.github.com/MobiDevelop/5514357
public class MultipleZipFileHandleResolver implements FileHandleResolver {

    private final Array<ZipFile> zips;

    public MultipleZipFileHandleResolver(Array<ZipFile> zips) {
        Objects.requireNonNull(zips);

        if (zips.size == 0) {
            throw new IllegalArgumentException("zip list is empty");
        }

        for (ZipFile zipFile : zips) {
            if (zipFile.size() == 0) {
                throw new IllegalStateException("zip file is empty: " + zipFile.getName());
            }
        }

        this.zips = zips;
    }

    @Override
    public FileHandle resolve(String fileName) {
        //search correct zip
        ZipFile zipFile = null;

        for (int i = 0; i < zips.size; i++) {
            ZipFile zipFile1 = zips.get(i);

            if (zipFile1 == null) {
                continue;
            }

            if (zipFile1.getEntry(fileName) != null) {
                //if there are multiple zip files with same filename, the zip file will be overriden (mod support)
                zipFile = zipFile1;
            }
        }

        if (zipFile == null) {
            throw new GdxRuntimeException("no assetpack found with asset path: " + fileName);
        }

        return new ArchiveFileHandle(zipFile, fileName);
    }

}
