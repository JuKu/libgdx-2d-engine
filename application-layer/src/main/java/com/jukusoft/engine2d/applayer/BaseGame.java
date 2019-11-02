package com.jukusoft.engine2d.applayer;

import com.badlogic.gdx.Gdx;
import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.engine2d.applayer.init.impl.CreateThreadsInitializer;
import com.jukusoft.engine2d.applayer.init.impl.SubSystemCreatorInitializer;
import com.jukusoft.engine2d.applayer.init.impl.UIThreadSubSystemsInitializer;
import com.jukusoft.engine2d.applayer.utils.SubSystemInitializer;
import com.jukusoft.engine2d.basegame.Game;
import com.jukusoft.engine2d.core.init.Initializer;
import com.jukusoft.engine2d.core.logger.Log;
import com.jukusoft.engine2d.core.shutdown.ErrorHandler;
import com.jukusoft.engine2d.core.subsystem.SubSystem;
import com.jukusoft.engine2d.core.subsystem.SubSystemManager;
import com.jukusoft.engine2d.core.subsystem.impl.DefaultSubSystemManager;
import com.jukusoft.engine2d.core.time.GameTime;

import java.util.List;
import java.util.function.Consumer;

public abstract class BaseGame extends BaseApp {

    private ObjectArrayList<SubSystem> subSystemsList;
    private Game game;
    private GameTime gameTime = GameTime.getInstance();

    public BaseGame(Class<?> gameClass) {
        super(gameClass);
    }

    protected abstract Consumer<SubSystemManager> addSubSystems(/*SubSystemManager manager*/);

    protected abstract Game createGame();

    /**
     * method where other game layers can add initializers to game init
     *
     * @param initializerList list with initializers to process
     */
    protected final void addInitializers(List<Initializer> initializerList) {
        try {
            SubSystemManager subSystemManager = new DefaultSubSystemManager("SubSystemManager");

            Log.i("BaseGame", "add subsystems");
            Consumer<SubSystemManager> subSystemCreator = this.addSubSystems(/*subSystemManager*/);

            //get subsystems for UI-thread
            this.subSystemsList = subSystemManager.listSubSystemsByThread(1);

            //initialize subsystems for UI-thread
            initializerList.add(new SubSystemCreatorInitializer(subSystemManager, subSystemCreator));
            initializerList.add(new UIThreadSubSystemsInitializer(subSystemManager.listSubSystemsByThread(1)));
            initializerList.add(new CreateThreadsInitializer(subSystemManager));
        } catch (Throwable e) {
            Log.e("BaseGame", "error occured during initialization: ", e);
            ErrorHandler.shutdownWithException(e);
        }
    }

    @Override
    protected final void onInitAfterSplashscreen() {
        this.game = createGame();

        //initialize subsystems
        SubSystemInitializer.init(subSystemsList);
    }

    @Override
    protected final void onUpdate() {
        //update timestamp and delta time for movements & animations
        //gameTime.setTime(System.currentTimeMillis());
        //System.out.println("delta: " + Gdx.graphics.getDeltaTime() + ", gameTime delta: " + gameTime.getDelta());
        gameTime.setDelta(Gdx.graphics.getDeltaTime());

        //update subsystems
        for (int i = 0; i < subSystemsList.size(); i++) {
            SubSystem subSystem = subSystemsList.get(i);
            subSystem.update();
        }
    }

    @Override
    protected final void onDispose() {
        //shutdown all subsystems
        for (int i = 0; i < subSystemsList.size(); i++) {
            SubSystem subSystem = subSystemsList.get(i);
            subSystem.shutdown();
        }
    }

}
