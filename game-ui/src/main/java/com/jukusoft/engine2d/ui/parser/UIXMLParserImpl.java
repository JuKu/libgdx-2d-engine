package com.jukusoft.engine2d.ui.parser;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.core.utils.StringUtils;
import com.jukusoft.engine2d.ui.UIScreen;
import com.jukusoft.engine2d.ui.Widget;
import com.jukusoft.engine2d.ui.dto.Soundtrack;
import com.jukusoft.engine2d.ui.factory.WidgetFactory;
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

            //parse screen id
            XdmItem idAttr = selectorCompiler.getSingleValue(XmlSelectors.SCREEN_ID, screen);
            String screenId = idAttr != null ? idAttr.getStringValue() : null;
            uiScreen.setId(screenId);

            //parse screen background
            XdmItem backgroundAttr = selectorCompiler.getSingleValue(XmlSelectors.SCREEN_BACKGROUND, screen);
            String background = backgroundAttr != null ? backgroundAttr.getStringValue() : null;
            uiScreen.setBackground(background);

            //parse properties
            XdmItem properties = selectorCompiler.getSingleValue(XmlSelectors.PROPERTIES, screen);
            parseProperties(uiScreen, properties);

            parseStyles(uiScreen, screen);

            parseSoundtracks(uiScreen, screen);

            parseContainer(uiScreen, screen);

            screenList.add(uiScreen);
        }

        return screenList;
    }

    private String getStringOrDefault(final Enum<?> selector, XdmItem parentItem, String defaultContent) throws SaxonApiException {
        XdmItem attr = selectorCompiler.getSingleValue(selector, parentItem);
        return attr != null ? attr.getStringValue() : defaultContent;
    }

    private int getIntegerOrDefault(final Enum<?> selector, XdmItem parentItem, int defaultValue) throws SaxonApiException {
        XdmItem attr = selectorCompiler.getSingleValue(selector, parentItem);
        return attr != null ? Integer.parseInt(attr.getStringValue()) : defaultValue;
    }

    private void parseProperties(UIScreen uiScreen, XdmItem properties) throws SaxonApiException {
        uiScreen.setPosX(getIntegerOrDefault(XmlSelectors.PROPERTY_XPOS, properties, 0));
        uiScreen.setPosY(getIntegerOrDefault(XmlSelectors.PROPERTY_YPOS, properties, 0));
        uiScreen.setWidth(getStringOrDefault(XmlSelectors.PROPERTY_WIDTH, properties, "parent"));
        uiScreen.setHeight(getStringOrDefault(XmlSelectors.PROPERTY_HEIGHT, properties, "parent"));
    }

    private void parseStyles(UIScreen uiScreen, XdmItem screen) throws SaxonApiException {
        XdmValue styles = selectorCompiler.getValue(XmlSelectors.STYLE, screen);

        for (XdmItem style : styles) {
            uiScreen.addStyle(style.getStringValue());
        }
    }

    private void parseSoundtracks(UIScreen uiScreen, XdmItem screen) throws SaxonApiException {
        XdmValue soundtracks = selectorCompiler.getValue(XmlSelectors.SOUNDTRACK, screen);

        for (XdmItem soundtrack : soundtracks) {
            String path = getStringOrDefault(XmlSelectors.ATTR_PATH, soundtrack, null);
            boolean loop = Boolean.parseBoolean(getStringOrDefault(XmlSelectors.ATTR_LOOP, soundtrack, "false"));
            float volume = Float.parseFloat(getStringOrDefault(XmlSelectors.ATTR_VOLUME, soundtrack, "1.0"));
            Soundtrack soundtrack1 = new Soundtrack(path, loop, volume);

            uiScreen.addSoundtrack(soundtrack1);
        }
    }

    private void parseContainer(UIScreen uiScreen, XdmItem screen) throws SaxonApiException {
        XdmItem container = selectorCompiler.getSingleValue(XmlSelectors.CONTAINER, screen);

        XdmValue buttons = selectorCompiler.getValue(XmlSelectors.BUTTON, container);

        for (XdmItem button : buttons) {
            //TODO: add code here
        }

        XdmValue customWidgetList = selectorCompiler.getValue(XmlSelectors.CUSTOM_WIDGETS, container);

        for (XdmItem customWidget : customWidgetList) {
            //call widget factory
            Widget widget = WidgetFactory.createCustomWidget(customWidget, selectorCompiler);
            uiScreen.addWidget(widget);
        }
    }

}
