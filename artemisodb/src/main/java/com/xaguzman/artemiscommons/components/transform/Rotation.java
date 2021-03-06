package com.xaguzman.artemiscommons.components.transform;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.artemis.annotations.PooledWeaver;
import com.xaguzman.artemiscommons.components.Tweenable;

/**
 * Created by Xavier Guzman on 3/31/2017.
 */
@PooledWeaver
public class Rotation extends Component implements Tweenable {
    public float angle;

    /** No rotation (angle = 0)*/
    public static final Rotation NONE = new Rotation();

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
