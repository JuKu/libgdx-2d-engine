package com.jukusoft.engine2d.view.screens;

import com.carrotsearch.hppc.ObjectArrayList;

/**
 * Created by Justin on 06.02.2017.
 */
public interface ScreenManager<T extends IScreen> {

    /**
     * add screen
     *
     * @param name name of screen
     * @param screen instance of screen
     */
    public void addScreen(final String name, T screen);

    /**
     * remove screen
     *
     * @param name name of screen
     */
    public void removeScreen(final String name);

    /**
     * push screen
     *
     * @param name name of screen
     */
    public void push(final String name);

    /**
     * leave all active game states and enter an new one
     *
     * @param name name of new game state
     */
    public void leaveAllAndEnter(final String name);

    /**
     * pop screen
     *
     * @return instance of top screen
     */
    public T pop();

    /**
    * get screen by name
     *
     * @param name unique name of screen
     *
     * @return instance of screen
    */
    public T getScreenByName(final String name);

    /**
     * list all initialized screens
     *
     * @return list with all registered streams
     */
    public ObjectArrayList<T> listScreens();

    /**
     * list all active screens
     *
     * @return list with all active screens
     */
    public ObjectArrayList<T> listActiveScreens();

    public void resize(int oldWidth, int oldHeight, int newWidth, int newHeight);

    /**
    * update all active screens
    */
    public void update(float delta);

    /**
    * beforeDraw all active screens
    */
    public void draw(float delta);

    /**
    * dispose screen manager with all screens
    */
    public void dispose();

}
