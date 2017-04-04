package com.xguzm.artemiscommons.components.transform;

import com.artemis.PooledComponent;

/**
 * Created by gdlxguzm on 3/31/2017.
 */
public class Rotation extends PooledComponent {
    public float angle;

    /** No rotation (angle = 0)*/
    public static final Rotation NONE = new Rotation();

    @Override
    protected void reset() {
        angle = 0;
    }
}
