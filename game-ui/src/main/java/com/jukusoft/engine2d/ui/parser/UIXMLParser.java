package com.jukusoft.engine2d.ui.parser;

import java.net.URI;

/**
 * parser to parse ui xml files
 */
public interface UIXMLParser {

    public void parse(URI uri);

    public void parse(String content);

}
