package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

/**
 * The velocity to move the around the screen.
 *
 * Created by gdlxguzm on 3/21/2015.
 */
public class Velocity extends PooledComponent{
    public Vector2 xy = new Vector2();
    public Vector2 maxXY = new Vector2();

    public Velocity() {
        this(0, 0, 1, 1);
    }

    public Velocity(float maxx, float maxy){
        this(maxx, maxy, maxx, maxy);
    }

    public Velocity(float x, float y, float maxx, float maxy){
        set(x, y , maxx, maxy);
    }

    public void set(float x, float y, float maxx, float maxy){
        xy.set(x, y);
        maxXY.set(maxx, maxy);
    }

    public float x(){
        return xy.x;
    }

    public float maxX() {return maxXY.x; };

    public float y(){
        return xy.y;
    }

    public float maxY() {return maxXY.y; };

    public void set(float maxx, float maxy){
        set(maxx, maxy, maxx, maxy);
    }

    @Override
    protected void reset() {
        xy.set(maxXY.scl(0));
    }
}
