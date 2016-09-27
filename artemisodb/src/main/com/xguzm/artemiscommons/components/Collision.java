package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Rectangle;

public class Collision extends PooledComponent {
    public Rectangle bounds = new Rectangle();
    public int categoryBits, categoryMaskBits;

    public void setBounds(float x, float y, float width, float height){
        bounds.set(x, y, width, height);
    }

    @Override
    protected void reset() {
        bounds.set(0, 0, 0, 0);
    }
}
