package com.jukusoft.engine2d.applayer.game;

import com.badlogic.gdx.maps.Map;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.basegame.service.Service;
import com.jukusoft.engine2d.core.time.GameTime;
import org.mini2Dx.gdx.utils.ObjectMap;

public class BasicGame implements Game {

    /**
    * is the game running or paused?
    */
    private boolean running = true;
    private boolean forcePause = false;

    ObjectMap<String,Object> properties = new ObjectMap<>();
    ObjectMap<Class<? extends Service>,Service> services = new ObjectMap<>();

    @Override
    public boolean isRunning() {
        return running && !forcePause;
    }

    @Override
    public boolean isPaused() {
        return !isRunning();
    }

    @Override
    public void pause() {
        running = false;
    }

    @Override
    public void resume() {
        running = true;
    }

    @Override
    public void forcePause(boolean value) {
        forcePause = value;
    }

    @Override
    public void setProperty(String name, Object obj) {
        properties.put(name, obj);
    }

    @Override
    public <T> T getProperty(String name, Class<T> cls) {
        Object value = properties.get(name);

        if (value == null) {
            throw new IllegalStateException("property '" + name + "' not set");
        }

        return cls.cast(value);
    }

    @Override
    public boolean getPropertyBoolean(String name) {
        return getProperty(name, Boolean.class);
    }

    @Override
    public int getPropertInt(String name) {
        return getProperty(name, Integer.class);
    }

    @Override
    public float getPropertyFloat(String name) {
        return getProperty(name, Float.class);
    }

    @Override
    public double getPropertyDouble(String name) {
        return getProperty(name, Double.class);
    }

    @Override
    public String getPropertyString(String name) {
        return getProperty(name, String.class);
    }

    @Override
    public <T extends Service> T getService(Class<T> cls) {
        Service service = services.get(cls);

        if (service == null) {
            throw new IllegalStateException("service not registered: " + cls.getCanonicalName());
        }

        return cls.cast(service);
    }

    @Override
    public <T extends Service> void putService(T service, Class<T> cls) {
        services.put(cls, service);
    }

    @Override
    public GameTime getTime() {
        return null;
    }

}
