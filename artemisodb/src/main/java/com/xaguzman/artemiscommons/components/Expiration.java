package com.xaguzman.artemiscommons.components;

import com.artemis.PooledComponent;

/**
 * Created by gdlxguzm on 6/6/2015.
 */
public class Expiration extends PooledComponent {
    public float timeLeft;

    public Expiration() {}

    public Expiration(float time){
        this.timeLeft = time;
    }

    @Override
    protected void reset() {
        timeLeft = 0;
    }
}
