package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;
import com.xguzm.artemiscommons.components.collidershapes.ColliderShape;

/**
 * Created by gdlxguzm on 3/28/2017.
 */
public class Collider extends PooledComponent {

    public ColliderShape shape;
    public int categoryBits, categoryMaskBits;

    public Collider(ColliderShape shape){
        this.shape = shape;
    }

    @Override
    protected void reset() {
        shape = null;
    }

    public float getLeft(){
        return shape.getLeft();
    }

    public float getBottom(){
        return shape.getBottom();
    }

    public float getTop(){
        return shape.getTop();
    }

    public float getRight(){
        return shape.getRight();
    }
}
