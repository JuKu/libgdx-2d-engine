package com.jukusoft.engine2d.ui;

/**
 * widget base class
 */
public abstract class Widget {

    /**
     * widget id like in JavaFX
     */
    private String id;

    //widget position
    private float xPos;
    private float yPos;

    //widget dimension (e.q. to detect, if mouse is inner widget)
    private int width;
    private int height;

    private boolean visible;

    /**
     * initialize widget with ui drawer, load required assets, if neccessary
     *
     * @param uiDrawer instance of ui drawer
     */
    protected final void init(UIDrawer uiDrawer) {
        //
    }

    /**
     * check, if mouse is inner widget
     *
     * @param mouseX
     * @param mouseY
     *
     * @return
     */
    public boolean isMouseInner(float mouseX, float mouseY) {
        return (mouseX >= xPos && mouseX <= xPos + width) && (mouseY >= yPos && mouseY <= yPos + height);
    }

    /**
     * this method is called, if mouse is inner widget.
     * Can be used to implement hover effects and so on
     *
     * @param mouseX absolute mouse x position
     * @param mouseY absolute mouse y position
     * @param relX relative mouse x position inner widget
     * @param relY relative mouse y position inner widget
     */
    public abstract void mouseHover(float mouseX, float mouseY, float relX, float relY);

    /**
     * this method is called, if mouse clicks into this widget
     * Can be used to implement hover effects and so on
     *
     * @param mouseX absolute mouse x position
     * @param mouseY absolute mouse y position
     * @param relX relative mouse x position inner widget
     * @param relY relative mouse y position inner widget
     */
    public abstract void mouseClick(float mouseX, float mouseY, float relX, float relY);

}
