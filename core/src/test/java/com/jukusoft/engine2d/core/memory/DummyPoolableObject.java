package com.jukusoft.engine2d.core.memory;

import org.mini2Dx.gdx.utils.Pool;

public class DummyPoolableObject implements Pool.Poolable {

    public boolean resetCalled = false;

    @Override
    public void reset() {
        resetCalled = true;
    }

}
