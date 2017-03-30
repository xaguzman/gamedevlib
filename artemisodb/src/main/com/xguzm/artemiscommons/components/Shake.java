package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;

/**
 * Created by gdlxguzm on 3/29/2017.
 */
public class Shake extends PooledComponent {

    public float intensity;

    @Override
    protected void reset() {
        intensity = 0;
    }
}
