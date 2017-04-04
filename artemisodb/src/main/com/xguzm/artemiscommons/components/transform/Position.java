package com.xguzm.artemiscommons.components.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.xguzm.artemiscommons.components.Tweenable;

/**
 * Created by Xavier on 16/07/2015.
 */
public class Position extends PooledComponent implements Tweenable {

    public final Vector2 xy = new Vector2();

    @Override
    protected void reset() {
        xy.setZero();
    }

    public void set(float x, float y){
        xy.set(x, y);
    }

    public float x(){ return xy.x; }

    public float y(){ return xy.y; }

//    @Override
//    public void linearTween(Position thiz, Position other, float value) {
//        float x = Interpolation.linear.apply(thiz.x(), other.x(), value);
//        float y = Interpolation.linear.apply(thiz.y(), other.y(), value);
//
//        thiz.xy.set(x,y);
//    }

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
