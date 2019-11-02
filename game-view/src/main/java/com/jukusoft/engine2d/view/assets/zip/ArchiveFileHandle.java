package com.jukusoft.engine2d.view.assets.zip;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.jukusoft.engine2d.core.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * file handle for files inside a (zip) archive
 * <p>
 * see also: https://gist.github.com/MobiDevelop/5514357
 */
public class ArchiveFileHandle extends FileHandle {

    final ZipFile archive;
    final ZipEntry archiveEntry;

    public ArchiveFileHandle(ZipFile archive, File file) {
        super(/*new File(file.getAbsolutePath().replace("\\", "/"))*/file, Files.FileType.Classpath);//Classpath, External

        Objects.requireNonNull(archive);
        Objects.requireNonNull(file);

        this.archive = archive;
        archiveEntry = this.archive.getEntry(file.getPath().replace("\\", "/"));
    }

    public ArchiveFileHandle(ZipFile archive, String fileName) {
        super(fileName.replace('\\', '/'), Files.FileType.Classpath);//Classpath, External

        Objects.requireNonNull(archive);
        StringUtils.checkNotNullAndNotEmpty(fileName, "fileName");

        this.archive = archive;
        this.archiveEntry = archive.getEntry(fileName.replace('\\', '/'));

        if (this.archiveEntry == null) {
            throw new IllegalStateException("file does not exists in zip archive: " + fileName);
        }
    }

    @Override
    public FileHandle child(String name) {
        name = name.replace('\\', '/');
        if (file.getPath().length() == 0) return new ArchiveFileHandle(archive, new File(name));
        return new ArchiveFileHandle(archive, new File(file, name));
    }

    @Override
    public FileHandle sibling(String name) {
        name = name.replace('\\', '/');
        if (file.getPath().length() == 0) throw new GdxRuntimeException("Cannot get the sibling of the root.");
        return new ArchiveFileHandle(archive, new File(file.getParent(), name));
    }

    @Override
    public FileHandle parent() {
        File parent = file.getParentFile();
        //System.err.println("parent: " + parent);

        if (parent == null) {
            if (type == Files.FileType.Absolute)
                parent = new File("/");
            else
                parent = new File("");
        }

        return new ArchiveFileHandle(archive, parent);
    }

    @Override
    public InputStream read() {
        try {
            return archive.getInputStream(archiveEntry);
        } catch (IOException e) {
            throw new GdxRuntimeException("File not found: " + file + " (Archive)");
        }
    }

    @Override
    public boolean exists() {
        return archiveEntry != null;
    }

    @Override
    public long length() {
        return archiveEntry.getSize();
    }

    @Override
    public long lastModified() {
        return archiveEntry.getTime();
    }

}
