package com.jukusoft.engine2d.core.memory;

import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.events.EventData;
import com.jukusoft.engine2d.core.logger.Log;
import org.mini2Dx.gdx.utils.ObjectMap;
import org.mini2Dx.gdx.utils.Pool;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Object pools to reuse memory to avoid memory allocation
 *
 * @see Pool
 */
public class Pools {

    private static final String MEMORY_LEAK_LOG_TAG = "MemoryLeak";

    private static boolean checkForMemoryLeaks = false;
    private static ObjectMap<Object, Long> memoryLeakMap = new ObjectMap<>(50);

    private static Lock lock = new ReentrantLock(true);

    protected Pools() {
        //
    }

    /**
     * get an instance of an object (maybe the instance was recycled)
     *
     * @param cls class name of instance to get
     * @param <T> type of instance
     * @return instance
     */
    public static <T> T get(Class<T> cls) {
        return get(cls, true);
    }

    /**
     * get an instance of an object (maybe the instance was recycled)
     *
     * @param cls             class name of instance to get
     * @param memoryDetection set to false, if the object isn't freed in 30 seconds, else to true
     * @param <T>             type of instance
     * @return instance
     */
    public static <T> T get(Class<T> cls, boolean memoryDetection) {
        //first, lock Pools
        lock.lock();

        Pool<T> pool = org.mini2Dx.gdx.utils.Pools.get(cls);
        //System.err.println("pool size: " + pool.getFree());
        T obj = null;

        try {
            obj = pool.obtain();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("freeObjects size: " + pool.getFree());
            throw e;
        }

        lock.unlock();

        if (obj == null) {
            try {
                obj = cls.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                Log.w("Memory", "Exception while instantiating a new Pool object: ", e);
            }
        }

        if (obj instanceof EventData) {
            if (((EventData) obj).getRefCount() != 1) {
                throw new IllegalStateException("refCount has to be 1, refCount: " + ((EventData) obj).getRefCount());
            }

            //initialize event
            ((EventData) obj).init();
        }

        //System.err.println("get(): " + obj.hashCode());

        if (checkForMemoryLeaks && memoryDetection) {
            //Log.d(MEMORY_LEAK_LOG_TAG, "add object to list");
            memoryLeakMap.put(obj, System.currentTimeMillis());
        }

        return obj;
    }

    public static <T> void free(T obj) {
        lock.lock();
        //System.err.println("free(): " + obj.hashCode());

        if (obj instanceof Pool.Poolable) {
            ((Pool.Poolable) obj).reset();
        }

        Pool<T> pool = (Pool<T>) org.mini2Dx.gdx.utils.Pools.get(obj.getClass());
        pool.free(obj);

        lock.unlock();

        if (checkForMemoryLeaks) {
            //Log.d(MEMORY_LEAK_LOG_TAG, "remove object to list");
            memoryLeakMap.remove(obj);
        }
    }

    public static void checkForMemoryLeaks() {
        checkForMemoryLeaks = Config.getBool("Pools", "checkForMemoryLeaks");

        //Log.i(MEMORY_LEAK_LOG_TAG, "run memory leak checker");

        if (!checkForMemoryLeaks) {
            //Log.d(MEMORY_LEAK_LOG_TAG, "memory leak checker is disabled");
            return;
        }

        long timeout = Config.getInt("Pools", "memoryLeakTimeOut");
        long now = System.currentTimeMillis();

        //Log.d(MEMORY_LEAK_LOG_TAG, memoryLeakMap.size + " entries in map");

        for (ObjectMap.Entry<Object, Long> entry : memoryLeakMap.entries()) {
            if (entry.value + timeout < now) {
                Log.w(MEMORY_LEAK_LOG_TAG, "memory leak detected, class was not be freed: " + entry.key.getClass().getCanonicalName());

                //don't show the same warning again
                memoryLeakMap.remove(entry.key);
            }
        }
    }

}
