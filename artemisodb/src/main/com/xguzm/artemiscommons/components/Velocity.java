package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

/**
 * The velocity to move around the screen.
 *
 * Created by gdlxguzm on 3/21/2015.
 */
public class Velocity extends PooledComponent implements Tweenable{
    public Vector2 xy = new Vector2();

    public static final Velocity NONE = new Velocity();

    public Velocity() {
        this(0, 0);
    }

    public Velocity(float x, float y){
        set(x, y );
    }

    public void set(float x, float y){
        xy.set(x, y);
    }

    public float x(){
        return xy.x;
    }

    public float y(){
        return xy.y;
    }

    @Override
    protected void reset() {
        xy.setZero();
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
