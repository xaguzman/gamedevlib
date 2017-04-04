package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Interpolation;
import com.sun.deploy.util.ArrayUtil;

/**
 * Starts a tween of the given type. Calling {@link #setType(Class)} is required.
 * Inconvenient, but only way to get through generics.
 */
public class Tween extends PooledComponent {
    public float duration, timeLeft, percent;
    public boolean reverse;
    public Interpolation easing;
    public Tweenable tweenable;
    private float[] targetValues;

    public Tween(){
        easing = Interpolation.linear;
        targetValues = new float[4];
    }

    Tween to(Tweenable target, float duration){
        this.tweenable = target;
        this.duration = duration;
        return this;
    }

    Tween targetValues(float[] targetValues){
        if (targetValues.length > this.targetValues.length){
            this.targetValues = new float[targetValues.length];
        }
        System.arraycopy(targetValues,0, this.targetValues, 0, targetValues.length);

        return this;
    }

    Tween easing(Interpolation interpolation){
        this.easing = interpolation;
        return this;
    }

    @Override
    protected void reset() {
        duration = timeLeft = percent = 0;
        reverse = false;
        easing = null;
        tweenable = null;
        targetValues = null;
    }


}
