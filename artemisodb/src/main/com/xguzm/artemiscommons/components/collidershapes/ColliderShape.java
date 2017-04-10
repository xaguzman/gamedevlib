package com.xguzm.artemiscommons.components.collidershapes;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by gdlxguzm on 3/28/2017.
 */
public abstract class ColliderShape {
    protected Vector2 position = new Vector2();
    public boolean collidesWith(ColliderShape other){
        if (other instanceof RectangleCollider){
            return collidesWith((RectangleCollider)other);
        }else if(other instanceof CircleCollider){
            return collidesWith((CircleCollider)other);
        }
        return false;
    }

    protected abstract boolean collidesWith(RectangleCollider rect);
    protected abstract boolean collidesWith(CircleCollider rect);

    public abstract float getWidth();
    public abstract void setWidth(float width);
    public abstract float getHeight();
    public abstract void setHeight(float height);
    public abstract float getTop();
    public abstract void setTop(float top);
    public abstract float getBottom();
    public abstract void setBottom(float bottom);
    public abstract float getLeft();
    public abstract void setLeft(float left);
    public abstract float getRight();
    public abstract void setRight(float right);
    public abstract void centerX();
    public abstract void centerY();
    public abstract void center();

    /**
     * The absolute xy of the collider, in world units
     * */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * The absolute xy of the collider, in world units
     * */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * The absolute xy of the collider, in world units
     * */
    public void setPosition(float x, float y){
        this.position.set(x, y);
    }
}
