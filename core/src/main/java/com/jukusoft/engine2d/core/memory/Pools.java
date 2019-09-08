package com.jukusoft.engine2d.core.memory;

import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.events.EventData;
import com.jukusoft.engine2d.core.logger.Log;
import org.mini2Dx.gdx.utils.ObjectMap;
import org.mini2Dx.gdx.utils.Pool;

/**
 * Object pools to reuse memory to avoid memory allocation
 *
 * @see Pool
 */
public class Pools {

    private static final String MEMORY_LEAK_LOG_TAG = "MemoryLeak";

    private static boolean checkForMemoryLeaks = false;
    private static ObjectMap<Object,Long> memoryLeakMap = new ObjectMap<>(50);

    protected Pools() {
        //
    }

    public static <T> T get(Class<T> cls) {
        T obj = org.mini2Dx.gdx.utils.Pools.get(cls).obtain();

        if (obj == null) {
            try {
                obj = cls.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                Log.w("Memory", "Exception while instantiating a new Pool object: ", e);
            }
        }

        if (obj instanceof EventData) {
            //initialize event
            ((EventData) obj).init();
        }

        if (checkForMemoryLeaks) {
            //Log.d(MEMORY_LEAK_LOG_TAG, "add object to list");
            memoryLeakMap.put(obj, System.currentTimeMillis());
        }

        return obj;
    }

    public static <T> void free(T obj) {
        if (obj instanceof Pool.Poolable) {
            ((Pool.Poolable) obj).reset();
        }

        Pool<T> pool = (Pool<T>) org.mini2Dx.gdx.utils.Pools.get(obj.getClass());
        pool.free(obj);

        if (checkForMemoryLeaks) {
            //Log.d(MEMORY_LEAK_LOG_TAG, "remove object to list");
            memoryLeakMap.remove(obj);
        }
    }

    public static void checkForMemoryLeaks () {
        checkForMemoryLeaks = Config.getBool("Pools", "checkForMemoryLeaks");

        //Log.i(MEMORY_LEAK_LOG_TAG, "run memory leak checker");

        if (!checkForMemoryLeaks) {
            //Log.d(MEMORY_LEAK_LOG_TAG, "memory leak checker is disabled");
            return;
        }

        long timeout = Config.getInt("Pools", "memoryLeakTimeOut");
        long now = System.currentTimeMillis();

        //Log.d(MEMORY_LEAK_LOG_TAG, memoryLeakMap.size + " entries in map");

        for (ObjectMap.Entry<Object,Long> entry : memoryLeakMap.entries()) {
            if (entry.value + timeout < now) {
                Log.w(MEMORY_LEAK_LOG_TAG, "memory leak detected, class was not be freed: " + entry.key.getClass().getCanonicalName());

                //don't show the same warning again
                memoryLeakMap.remove(entry.key);
            }
        }
    }

}
