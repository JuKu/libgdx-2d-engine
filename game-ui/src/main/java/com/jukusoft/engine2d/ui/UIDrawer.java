package com.jukusoft.engine2d.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.net.URI;

public interface UIDrawer {

    /**
     * load from xml file#
     *
     * @param uri uri to xml file
     */
    public void load(URI uri);

    /**
     * add a widget to ui drawer and draw it
     *
     * @param widget widget to add
     */
    public void addWidget(Widget widget);

    /**
     * remove a widget from ui drawer
     *
     * @param widget widget to remove
     */
    public void removeWidget(Widget widget);

    /**
     * reload all widgets from xml file completely
     */
    public void reload();

    public void update(float delta);

    public void draw(SpriteBatch batch);

    /**
     * find widget by id
     *
     * @param id id of the widget
     * @param cls return class
     * @param <T> widget type
     *
     * @return widget with the specific id
     */
    public <T extends Widget> T findWidgetbyId(String id, Class<T> cls);

}
