package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

/**
 * Created by gdlxguzm on 3/31/2017.
 */
public class Tint extends PooledComponent implements Tweenable<Tint> {
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
    public void linearTween(Tint thiz, Tint other, float value) {
        float r = Interpolation.linear.apply(thiz.color.r, other.color.r, value);
        float g = Interpolation.linear.apply(thiz.color.g, other.color.g, value);
        float b = Interpolation.linear.apply(thiz.color.b, other.color.b, value);
        float a = Interpolation.linear.apply(thiz.color.a, other.color.a, value);

        thiz.color.set(r, g, b, a);
    }
}
