package com.jukusoft.engine2d.core.subsystem.impl;

import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.utils.StringUtils;
import com.jukusoft.engine2d.core.utils.Threads;
import org.mini2Dx.gdx.utils.ObjectMap;

/**
 * default subsystem manager. Every thread has it's own subsystem manager
 */
public class DefaultSubSystemManager implements SubSystemManager {

    //list with subsystems
    protected ObjectMap<Integer, ObjectArrayList<SubSystem>> subSystemsThreadsMap = new ObjectMap<>(10);

    /**
     * name of subsystem manager
     */
    private final String name;

    public DefaultSubSystemManager(String name) {
        StringUtils.checkNotNullAndNotEmpty(name, "name");
        this.name = name;
    }

    /*@Override
    public void init(List<Initializer> initializers) {
        Objects.requireNonNull(initializers);

        //check, if current thread is main thread
        ThreadUtils.checkIfMainThread();

        //initialize subsystems in main thread
        this.subSystems.iterator().forEachRemaining(system -> system.value.init(initializers));
    }*/

    @Override
    public void addSubSystem(SubSystem system, int threadID) {
        if (threadID < 0 || threadID > Threads.getThreadCount())
            throw new IllegalArgumentException("threadID is out of bounds (min: 1, max: " + Threads.getThreadCount() + ")");

        checkIfKeyExists(threadID);

        ObjectArrayList<SubSystem> subSystems = subSystemsThreadsMap.get(threadID);
        subSystems.add(system);

        Log.i("SubSystems_" + getName(), "added subsystem " + system.getClass().getCanonicalName());
    }

    @Override
    public void removeSubSystem(SubSystem system) {
        subSystemsThreadsMap.forEach(entry -> {
            ObjectArrayList<SubSystem> subSystems = entry.value;
            subSystems.removeAll(system);
        });

        Log.i("SubSystems_" + getName(), "removed subsystem " + system.getClass().getCanonicalName());
    }

    @Override
    public ObjectArrayList<SubSystem> listSubSystemsByThread(int threadID) {
        checkIfKeyExists(threadID);
        return subSystemsThreadsMap.get(threadID);
    }

    private void checkIfKeyExists(int threadID) {
        if (!subSystemsThreadsMap.containsKey(threadID)) {
            subSystemsThreadsMap.put(threadID, new ObjectArrayList<>());
        }
    }

    /*@Override
    public void run(int threadID) {
        for (int i = 0; i < subSystems.size(); i++) {
            subSystems.get(i).update();
        }
    }*/

    public String getName() {
        return name;
    }

}
