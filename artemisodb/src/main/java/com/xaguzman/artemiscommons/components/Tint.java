package com.xaguzman.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Xavier Guzman on 3/31/2017.
 */
public class Tint extends PooledComponent implements Tweenable {
    public final Color color = new Color();

    public static final Tint WHITE = new Tint(Color.WHITE);

    public Tint(){
        this(Color.WHITE.cpy());
    }

    public Tint(Color color){
        this.color.set(color);
    }

    @Override
    protected void reset() {
        color.set(Color.WHITE);
    }

    @Override
    public int getNumComponents() {
        return 4;
    }

    @Override
    public void setTweenableValues(float[] newValues) {
        color.r = newValues[0];
        color.g = newValues[1];
        color.b = newValues[2];
        color.a = newValues[3];
    }

    @Override
    public void getTweenableValues(float[] returnValues) {
        returnValues[0] = color.r;
        returnValues[1] = color.g;
        returnValues[2] = color.b;
        returnValues[3] = color.a;
    }
}
