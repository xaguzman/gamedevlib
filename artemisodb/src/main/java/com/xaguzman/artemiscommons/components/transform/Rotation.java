package com.xaguzman.artemiscommons.components.transform;

import com.artemis.PooledComponent;
import com.xaguzman.artemiscommons.components.Tweenable;

/**
 * Created by gdlxguzm on 3/31/2017.
 */
public class Rotation extends PooledComponent implements Tweenable {
    public float angle;

    /** No rotation (angle = 0)*/
    public static final Rotation NONE = new Rotation();

    @Override
    protected void reset() {
        angle = 0;
    }

    @Override
    public int getNumComponents() {
        return 1;
    }

    @Override
    public void setTweenableValues(float[] newValues) {
        this.angle = newValues[0];
    }

    @Override
    public void getTweenableValues(float[] returnValues) {
        returnValues[0] = angle;
    }
}
