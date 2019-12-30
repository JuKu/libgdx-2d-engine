package com.jukusoft.engine2d.ui.parser;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.core.utils.StringUtils;
import com.jukusoft.engine2d.ui.UIScreen;
import net.sf.saxon.s9api.*;

import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class UIXMLParserImpl implements UIXMLParser {

    private SelectorCompiler selectorCompiler;

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

        return parseContent(content);
    }

    protected Array<UIScreen> parseContent(String content) {
        XdmNode root;

        try {
            root = getRootNode(content);
            return parseRootNode(root);
        } catch (SaxonApiException e) {
            e.printStackTrace();
            throw new IllegalStateException("Exception while parsing xml: ", e);
        }
    }

    private XdmNode getRootNode(String content) throws SaxonApiException {
        Processor processor = new Processor(false);
        DocumentBuilder saxBuilder = processor.newDocumentBuilder();
        saxBuilder.setLineNumbering(true);

        StringReader sr = new StringReader(content);
        StreamSource stringSource = new StreamSource(sr);
        //transformer.setParameter(name, ss);

        XdmNode doc = saxBuilder.build(stringSource);
        selectorCompiler = new SelectorCompiler(doc, processor);

        return doc;
    }

    private Array<UIScreen> parseRootNode(XdmNode root) throws SaxonApiException {
        Array<UIScreen> screenList = new Array<>();

        XdmValue screens = selectorCompiler.getValue(XmlSelectors.SCREEN);

        for (XdmItem screen : screens) {
            UIScreen uiScreen = new UIScreen();

            //TODO: add code here

            screenList.add(uiScreen);
        }

        return screenList;
    }

}
