package com.jukusoft.engine2d.applayer.init.impl;

import com.jukusoft.engine2d.applayer.init.InitPriority;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.task.TaskManager;
import com.jukusoft.engine2d.core.task.TaskManagers;
import com.jukusoft.engine2d.core.task.impl.DefaultTaskManager;
import com.jukusoft.engine2d.core.utils.Threads;

@InitPriority(6)
public class TaskManagerInitializer implements Initializer {

    @Override
    public void init() throws Exception {
        Log.i("Tasks", "initialize task manager");

        //register task managers
        TaskManager mainTaskManager = new DefaultTaskManager("main");
        TaskManagers.register(Threads.UI_THREAD, mainTaskManager);
    }

}
