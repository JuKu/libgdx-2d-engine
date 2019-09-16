package com.jukusoft.engine2d.applayer.game;

import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.basegame.service.Service;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.time.GameSpeed;
import com.jukusoft.engine2d.core.time.GameTime;
import org.mini2Dx.gdx.utils.ObjectMap;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * service locator for the game
 */
public class BasicGame implements Game {

    /**
     * is the game running or paused?
     */
    private boolean running = false;
    private boolean forcePause = false;

    ObjectMap<String, Object> properties = new ObjectMap<>();
    ObjectMap<Class<? extends Service>, Service> services = new ObjectMap<>();

    private ScheduledExecutorService executorService;
    private GameSpeed gameSpeed = new GameSpeed();

    public BasicGame() {
        if (Config.getBool("ExecutorService", "enabled")) {
            this.executorService = Executors.newScheduledThreadPool(Config.getInt("ExecutorService", "threadCount"));
        }
    }

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
    public GameSpeed getSpeedInstance() {
        return gameSpeed;
    }

    @Override
    public float getGameSpeed() {
        return this.gameSpeed.getSpeed();
    }

    @Override
    public void setGameSpeed(float gameSpeed) {
        this.gameSpeed.setSpeed(gameSpeed);
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

    @Override
    public ScheduledExecutorService getExecutorService() {
        if (this.executorService == null) {
            throw new UnsupportedOperationException("executor service is disabled in configuration, see ExecutorService.enabled");
        }

        return this.executorService;
    }

}
