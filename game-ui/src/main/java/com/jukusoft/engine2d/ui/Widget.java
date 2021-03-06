package com.jukusoft.engine2d.ui;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.engine2d.ui.parser.SelectorCompiler;
import com.jukusoft.engine2d.ui.style.UIStyleManager;
import com.jukusoft.engine2d.view.assets.assetmanager.GameAssetManager;
import net.sf.saxon.s9api.XdmItem;

import java.util.Objects;

/**
 * widget base class
 */
public abstract class Widget extends InputAdapter {

    /**
     * widget id like in JavaFX
     */
    private String id;

    //widget position
    private float xPos;
    private float yPos;
    private float zPos = 1;

    //widget dimension (e.q. to detect, if mouse is inner widget)
    private int width;
    private int height;

    //scaling
    private float scaleX = 1;
    private float scaleY = 1;

    private Array<String> requiredFeaturesTags = new Array<>();
    private boolean visible;

    /**
     * initialize widget with ui drawer, load required assets, if neccessary
     */
    protected final void init(UIStyleManager styleManager, GameAssetManager assetManager) {
        initWidget(styleManager, assetManager);
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

    public void setId(String id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    public float getxPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public float getzPos() {
        return zPos;
    }

    public void setZPos(float zPos) {
        this.zPos = zPos;
    }

    public void setPosition(float x, float y, float z) {
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void addRequiredFeatureTag(String featureTag) {
        requiredFeaturesTags.add(featureTag);
    }

    public void removeRequiredFeatureTag(String featureTag) {
        requiredFeaturesTags.removeValue(featureTag, false);
    }

    public Array<String> listRequiredFeatureTags() {
        return requiredFeaturesTags;
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

    /**
     * this method can be overriden, if custom widgets want to parse specific attributes from xml
     * @param widgetItem
     * @param selectorCompiler
     */
    public void parseFromXml(XdmItem widgetItem, SelectorCompiler selectorCompiler) {
        //
    }

    public final void disposeWidget(UIStyleManager styleManager, GameAssetManager assetManager) {
        dispose(styleManager, assetManager);
    }

    /**
     * this method can be overriden to do some things on widget initialization
     *
     * @param styleManager style manager to get current style
     * @param assetManager asset manager to load and unload assets
     */
    protected void initWidget(UIStyleManager styleManager, GameAssetManager assetManager) {
        //
    }

    /**
     * this method can be overriden to dispose widget, e.q. unload assets and so on
     *
     * @param styleManager style manager to get current style
     * @param assetManager asset manager to load and unload assets
     */
    protected void dispose(UIStyleManager styleManager, GameAssetManager assetManager) {
        //
    }

}
