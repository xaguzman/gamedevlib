package com.xaguzman.artemiscommons.systems.base;

import com.artemis.BaseSystem;

/**
 * Created by gdlxguzm on 4/3/2017.
 */
public class PassiveSystem extends BaseSystem {

    public PassiveSystem() {

    }

    @Override
    protected void processSystem() {
        // do nothing!
        setEnabled(false);
    }

    @Override
    protected boolean checkProcessing() {
        return isEnabled();
    }
}