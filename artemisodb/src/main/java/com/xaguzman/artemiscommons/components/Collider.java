package com.xaguzman.artemiscommons.components;

import com.artemis.PooledComponent;
import com.xaguzman.artemiscommons.components.collidershapes.ColliderShape;

/**
 * Created by Xavier Guzman on 3/28/2017.
 */
public class Collider extends PooledComponent {

    public ColliderShape shape;
    public int categoryBits = 1, categoryMaskBits = 1;
    public boolean isBullet;

    public Collider(){}

    public Collider(ColliderShape shape){
        this.shape = shape;
    }

    @Override
    protected void reset() {
        shape = null;
        isBullet = false;
        categoryBits = categoryMaskBits = 1;
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
