package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;

/**
 * Created by gdlxguzm on 4/3/2017.
 */
public class Sprite extends PooledComponent {
    public String name;

    /**  The layer to draw the sprite, lower values go at the back */
    public int layer;

    @Override
    protected void reset() {
        name = "";
        layer = 0;
    }
}
