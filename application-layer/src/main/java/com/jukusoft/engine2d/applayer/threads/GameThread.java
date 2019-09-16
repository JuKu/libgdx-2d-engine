package com.jukusoft.engine2d.applayer.threads;

import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.applayer.utils.SubSystemInitializer;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.logger.Log;

public class GameThread implements Runnable {

    private final ObjectArrayList<SubSystem> subSystemList;
    private final int threadID;
    private final String logTag;
    private final long timePerGameLogicGameloopTick;

    public GameThread(ObjectArrayList<SubSystem> subSystemList, int threadID) {
        this.subSystemList = subSystemList;
        this.threadID = threadID;
        logTag = "Thread-" + threadID;

        timePerGameLogicGameloopTick = Config.getInt("Threads", "timePerGameLogicGameloopTick");
    }

    @Override
    public void run() {
        Log.i(logTag, "initialize " + subSystemList.size() + " subsystems now");

        //init subsystems which belongs to this thread
        SubSystemInitializer.init(subSystemList);

        //call subsystems which has to be executed in main thread
        long startTime = 0;
        long endTime = 0;
        long diffTime = 0;

        Log.i(logTag, "enter gameloop");

        //gameloop
        while (!Thread.interrupted()) {
            startTime = System.currentTimeMillis();

            try {
                //update all subsystems
                for (int i = 0; i < subSystemList.size(); i++) {
                    SubSystem subSystem = subSystemList.get(i);
                    subSystem.update();
                }
            } catch (Exception e) {
                Log.w(logTag, "Uncatched exception in thread " + threadID + ": " + e.getLocalizedMessage(), e);
            }

            endTime = System.currentTimeMillis();
            diffTime = endTime - startTime;

            if (diffTime > timePerGameLogicGameloopTick - 1) {
                Log.w(logTag, "thread " + threadID + " required " + diffTime + "ms to execute the gameloop.");
            } else {
                try {
                    Thread.sleep(timePerGameLogicGameloopTick - diffTime);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        Log.i(logTag, "thread-" + threadID + " is shutting down now");

        Log.i(logTag, "shutdown all subsystems in thread " + threadID);
        this.subSystemList.iterator().forEachRemaining(system -> system.value.shutdown());

        Log.i(logTag, "closing thread " + threadID + " now.");
    }

}
