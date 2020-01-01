package com.jukusoft.engine2d.ui;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.net.URI;

public interface UIDrawer extends InputProcessor {

    /**
     * load from xml file
     */
    public void load(String xmlPath);

    /**
     * add a widget to ui drawer and draw it
     *
     * @param widget widget to add
     */
    //public void addWidget(Widget widget);

    /**
     * remove a widget from ui drawer
     *
     * @param widget widget to remove
     */
    //public void removeWidget(Widget widget);

    /**
     * reload all widgets from xml file completely
     */
    public void reload();

    /**
     * update widgets, e.q. call onClick listener and so on
     *
     * @param delta delta time
     */
    public void update(float delta);

    /**
     * draw widget
     */
    public void draw();

    /**
     * find widget by id
     *
     * @param id id of the widget
     * @param cls return class
     * @param <T> widget type
     *
     * @throws WidgetNotFoundException, if no widget with this id was found
     *
     * @return widget with the specific id or null, if not widget with this id was found
     */
    public <T extends Widget> T findWidgetbyId(String id, Class<T> cls) throws WidgetNotFoundException;

    /**
     * set debug mode, e.q. if debug mode is enabled, you can refresh ui with F5
     *
     * @param debugMode flag, if debug mode is enabled
     */
    public void setDebug(boolean debugMode);

    /**
     * check, if ui drawer is in debug mode
     *
     * @return true, if ui drawer is in debug mode
     */
    public boolean isDebug();

    /**
     * add activated feature tag (e.q. to show specific buttons)
     *
     * @param featureTag feature tag to enable
     */
    public void addFeatureTag(String featureTag);

    /**
     * remove activated feature tag (e.q. to show specific buttons)
     *
     * @param featureTag feature tag to enable
     */
    public void removeFeatureTag(String featureTag);

    /**
     * check, if the feature tag is available
     *
     * @param featureTag feature tag to check, if available
     *
     * @return true, if feature tag is available
     */
    public boolean hasFeatureTag(String featureTag);

    /**
     * window was resized
     *
     * @param oldWidth  old window width
     * @param oldHeight old window height
     * @param newWidth  new window width
     * @param newHeight new window height
     */
    public void onResize(int oldWidth, int oldHeight, int newWidth, int newHeight);

}
