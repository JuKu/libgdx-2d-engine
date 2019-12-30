package com.jukusoft.engine2d.ui.parser;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.core.utils.StringUtils;
import com.jukusoft.engine2d.ui.UIScreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UIXMLParserImpl implements UIXMLParser {

    @Override
    public Array<UIScreen> parse(FileHandle handle) {
        String content = handle.readString("UTF-8");

        if (content.isEmpty()) {
            throw new IllegalStateException("content cannot be empty");
        }

        return parseContent(content);
    }

    @Override
    public Array<UIScreen> parse(File baseDir, String content) throws IOException {
        if (!baseDir.exists()) {
            throw new FileNotFoundException("base dir not found: " + baseDir.getAbsolutePath());
        }

        StringUtils.checkNotNullAndNotEmpty(content, "content");

        //TODO: add code here

        return parseContent(content);
    }

    protected Array<UIScreen> parseContent(String content) {
        return null;
    }

}
