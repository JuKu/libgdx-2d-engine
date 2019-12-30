package com.jukusoft.engine2d.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.engine2d.ui.input.UIInputProcessor;

/**
 * widget base class
 */
public abstract class Widget implements UIInputProcessor {

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

    public String getId() {
        return id;
    }

    public float getxPos() {
        return xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * find widget by id
     *
     * @param id id of the widget
     * @param cls return class
     * @param <T> widget type
     *
     * @return widget with the specific id
     */
    public <T extends Widget> T findWidgetbyId(String id, Class<T> cls) {
        if (this.id.equals(id)) {
            return cls.cast(this);
        }

        return null;
    }

    /**
     * update widgets, e.q. call onClick listener and so on
     *
     * @param delta delta time
     * @param offsetX offset x, e.q. used for scrolling
     * @param offsetY offset y, e.q. used for scrolling
     */
    public abstract void update(float delta, float offsetX, float offsetY);

    /**
     * draw widget
     *
     * @param batch sprite batch
     * @param offsetX offset x, e.q. used for scrolling
     * @param offsetY offset y, e.q. used for scrolling
     */
    public abstract void draw(SpriteBatch batch, float offsetX, float offsetY);

}
