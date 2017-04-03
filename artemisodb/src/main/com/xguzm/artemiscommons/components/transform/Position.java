package com.xguzm.artemiscommons.components.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Xavier on 16/07/2015.
 */
public class Position extends PooledComponent {

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
}
