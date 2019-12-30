package com.jukusoft.engine2d.ui.factory;

import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.ui.Widget;
import com.jukusoft.engine2d.ui.WidgetFactoryException;
import com.jukusoft.engine2d.ui.parser.SelectorCompiler;
import com.jukusoft.engine2d.ui.parser.XmlSelectors;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmValue;

import java.lang.reflect.InvocationTargetException;

public class WidgetFactory {

    private WidgetFactory() {
        //
    }

    public static Widget createCustomWidget(XdmItem widgetItem, SelectorCompiler selectorCompiler) throws SaxonApiException {
        XdmItem typeValue = selectorCompiler.getSingleValue(XmlSelectors.CUSTOM_WIDGETS_CLASS, widgetItem);

        if (typeValue == null) {
            throw new IllegalStateException("no attribute 'type' is given for custom widget in ui screen xml");
        }

        String widgetClass = typeValue.getStringValue();

        Class cls = null;
        try {
            cls = Class.forName(widgetClass);
            Widget widget =
                    (Widget) cls.getConstructor().newInstance();

            //call method, so custom widgets can parse custom attributes from xml tag
            widget.parseFromXml(widgetItem, selectorCompiler);

            return widget;
        } catch (ClassNotFoundException e) {
            throw new WidgetFactoryException("widget class not found: " + widgetClass, e);
        } catch (InstantiationException e) {
            throw new WidgetFactoryException("Cannot create instance of widget class: " + widgetClass, e);
        } catch (InvocationTargetException e) {
            throw new WidgetFactoryException("InvocationTargetException: " + widgetClass, e);
        } catch (NoSuchMethodException e) {
            throw new WidgetFactoryException("Cannot found default constructor for widget class: " + widgetClass, e);
        } catch (IllegalAccessException e) {
            throw new WidgetFactoryException("IllegalAccessException: " + widgetClass, e);
        }
    }

}
