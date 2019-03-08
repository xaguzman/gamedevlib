package com.xaguzman.artemiscommons.components.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;
import com.xaguzman.artemiscommons.components.Tweenable;

/**
 * Created by Xavier on 16/07/2015.
 */
public class Position extends PooledComponent implements Tweenable {

    public final Vector2 xy = new Vector2();
    public final Vector2 prev = new Vector2();

    @Override
    protected void reset() {
        xy.setZero();
    }

    public void set(float x, float y){
        xy.set(x, y);
    }

    public float x(){ return xy.x; }

    public float y(){ return xy.y; }

    @Override
    public int getNumComponents() {
        return 2;
    }

    @Override
    public void setTweenableValues(float[] newValues) {
        xy.set(newValues[0], newValues[1]);
    }

    @Override
    public void getTweenableValues(float[] returnValues) {
        returnValues[0] = x();
        returnValues[1] = y();
    }
}
