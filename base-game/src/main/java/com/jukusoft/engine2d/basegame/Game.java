package com.jukusoft.engine2d.basegame;

import com.jukusoft.engine2d.basegame.service.Service;
import com.jukusoft.engine2d.core.time.GameSpeed;
import com.jukusoft.engine2d.core.time.GameTime;

import java.util.concurrent.ScheduledExecutorService;

/**
 * service locator for the game
 */
public interface Game {

    public boolean isRunning();

    public boolean isPaused();

    public void pause();

    public void resume();

    public void forcePause(boolean value);

    public void setProperty(String name, Object obj);

    public <T> T getProperty(String name, Class<T> cls);

    public boolean getPropertyBoolean(String name);

    public int getPropertInt(String name);

    public float getPropertyFloat(String name);

    public double getPropertyDouble(String name);

    public String getPropertyString(String name);

    public GameSpeed getSpeedInstance();

    public float getGameSpeed();

    public void setGameSpeed(float gameSpeed);

    public <T extends Service> T getService(Class<T> cls);

    public <T extends Service> void putService(T service, Class<T> cls);

    public GameTime getTime();

    public ScheduledExecutorService getExecutorService();

    //TODO: add code here

    public static Game getInstance() {
        return GameInstanceHolder.getInstance();
    }

}
