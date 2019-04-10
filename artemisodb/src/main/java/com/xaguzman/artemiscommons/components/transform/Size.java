package com.xaguzman.artemiscommons.components.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;
import com.xaguzman.artemiscommons.components.Tweenable;

/**
 * Created by Xavier Guzman on 3/31/2017.
 */
public class Size extends PooledComponent implements Tweenable{
    public final Vector2 xy = new Vector2();

    public float x(){
        return xy.y;
    }

    public float y(){
        return xy.y;
    }

    @Override
    protected void reset() {
        xy.setZero();
    }

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
