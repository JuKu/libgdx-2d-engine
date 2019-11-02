package com.jukusoft.engine2d.view.screens;

/**
 * Screen interface - screens are responsible for drawing, not for updating your game state!
 * <p>
 * Created by Justin on 06.02.2017.
 */
public interface IScreen {

    /**
     * method which should be executed if screen is created
     */
    public void onStart(ScreenManager<IScreen> screenManager);

    /**
     * method which should be executed if screen has stopped
     */
    public void onStop(ScreenManager<IScreen> screenManager);

    /**
     * method is executed, if screen is set to active state now.
     */
    public void onResume(ScreenManager<IScreen> screenManager);

    /**
     * method is executed, if screen isn't active anymore
     */
    public void onPause(ScreenManager<IScreen> screenManager);

    /**
     * window was resized
     *
     * @param oldWidth  old window width
     * @param oldHeight old window height
     * @param newWidth  new window width
     * @param newheight new window height
     */
    public void onResize(int oldWidth, int oldHeight, int newWidth, int newheight);

    /**
     * update game screen
     */
    public void update(ScreenManager<IScreen> screenManager, float delta);

    /**
     * beforeDraw game screen
     */
    public void draw(float delta);

}
