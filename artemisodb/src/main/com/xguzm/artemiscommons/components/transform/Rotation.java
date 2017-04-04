package com.xguzm.artemiscommons.components.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Interpolation;
import com.xguzm.artemiscommons.components.Tween;
import com.xguzm.artemiscommons.components.Tweenable;

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
    public void setTweenableValues(float[] newValues) {
        this.angle = newValues[0];
    }

    @Override
    public void getTweenableValues(float[] returnValues) {
        returnValues[0] = angle;
    }
}
