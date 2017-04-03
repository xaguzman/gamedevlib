package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by gdlxguzm on 3/31/2017.
 */
public class Tint extends PooledComponent {
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
}
