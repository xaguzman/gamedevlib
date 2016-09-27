package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Xavier on 16/07/2015.
 */
public class Position extends PooledComponent {

    public Vector2 xy = new Vector2();

    @Override
    protected void reset() {
        xy.set(0, 0);
    }

    public void set(float x, float y){
        xy.set(x, y);
    }

    public float x(){ return xy.x; }

    public float y(){ return xy.y; }
}
