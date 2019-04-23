package com.xaguzman.artemiscommons.components.collidershapes;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Xavier Guzman on 3/28/2017.
 */
public abstract class ColliderShape {
    protected final Vector2 previousPosition = new Vector2();
    protected final Vector2 position = new Vector2();

    public static final int TYPE_CIRCLE = 1;
    public static final int TYPE_RECTANGLE = 2;

    public boolean collidesWith(ColliderShape other){
        if (other.getShapeType() == TYPE_CIRCLE){
            return collidesWith((CircleCollider)other);
        }else if (other.getShapeType() == TYPE_RECTANGLE){
            return collidesWith((RectangleCollider)other);
        }
        return false;
    }

    public abstract int getShapeType();

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

    public Vector2 getPreviousPosition() { return previousPosition; }

    /**
     * The absolute xy of the collider, in world units
     * */
    public void setPosition(Vector2 position) {
        previousPosition.set(this.position);
        this.position.set(position);
        moved();
    }

    public void move(Vector2 delta){
        previousPosition.set(position);
        position.add(delta);
        moved();
    }

    abstract protected void moved();

    /**
     * The absolute xy of the collider, in world units
     * */
    public void setPosition(float x, float y){
        this.position.set(x, y);
    }
}
