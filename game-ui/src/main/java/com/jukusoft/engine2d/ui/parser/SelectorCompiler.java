package com.jukusoft.engine2d.ui.parser;

import com.jukusoft.engine2d.core.logger.Log;
import net.sf.saxon.s9api.*;

import java.util.HashMap;
import java.util.Map;

public class SelectorCompiler {

    private final Map<Enum<?>, XPathSelector> cachedSelectors = new HashMap<>();
    private final XdmNode doc;
    private final Processor processor;

    protected SelectorCompiler(XdmNode doc, Processor processor) {
        this.doc = doc;
        this.processor = processor;
    }

    public XPathSelector select(Enum<?> path) {
        if (!cachedSelectors.containsKey(path)) {

            try {
                XPathSelector selector = processor.newXPathCompiler()
                        .compile(path.toString())
                        .load();

                //add selector to cache
                cachedSelectors.put(path, selector);
            } catch (SaxonApiException e) {
                Log.e(SelectorCompiler.class.getSimpleName(), "SaxonApiException: ", e);
            }
        }

        return cachedSelectors.get(path);
    }

    public XdmValue getValue(final Enum<?> selector) throws SaxonApiException {
        return getValue(selector, doc);
    }

    public XdmValue getValue(final Enum<?> selector, XdmItem parentItem) throws SaxonApiException {
        XPathSelector s = select(selector);
        s.setContextItem(parentItem);

        return s.evaluate();
    }

    public XdmItem getSingleValue(final Enum<?> selector) throws SaxonApiException {
        return getSingleValue(selector, doc);
    }

    public XdmItem getSingleValue(final Enum<?> selector, XdmItem parentItem) throws SaxonApiException {
        XPathSelector s = select(selector);
        s.setContextItem(parentItem);

        return s.evaluateSingle();
    }

}
