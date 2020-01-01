package com.jukusoft.engine2d.ui.parser;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.ui.UIScreen;

import java.io.File;
import java.io.IOException;

/**
 * parser to parse ui xml files
 */
public interface UIXMLParser {

    public Array<UIScreen> parse(FileHandle handle);

    public Array<UIScreen> parse(File baseDir, String content) throws IOException;

    public UIScreen parseScreen(FileHandle handle) throws IOException;

}
