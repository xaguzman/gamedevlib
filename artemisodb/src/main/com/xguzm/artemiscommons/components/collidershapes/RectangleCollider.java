package com.xguzm.artemiscommons.components.collidershapes;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A rectangle collider, with left, right, top and bottom relative to its xy (which is world units)
 * Created by gdlxguzm on 3/28/2017.
 */
public class RectangleCollider extends ColliderShape{

    Rectangle bounds = new Rectangle();
    private float width, height;


    public RectangleCollider(float width, float height){
        this(width, height, 0, 0);
    }

    public RectangleCollider(float x, float y, float width, float height){
        this.width = width;
        this.height = height;
        position.set(x, y);
        setRect();
    }

    @Override
    protected boolean collidesWith(RectangleCollider rect) {
        setRect();
        return this.bounds.overlaps(rect.bounds);
    }

    @Override
    protected boolean collidesWith(CircleCollider circle) {
        setRect();
        return Intersector.overlaps(circle.bounds, this.bounds);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getLeft() {
        return position.x;
    }

    @Override
    public float getTop() {
        return position.y + height;
    }

    @Override
    public float getRight(){
        return position.x + width;
    }

    @Override
    public float getBottom() {
        return position.y;
    }

    @Override
    public void setBottom(float bottom) {
        position.y = bottom;
        setRect();
    }

    @Override
    public void setRight(float right) {
        position.x = right - width;
        setRect();
    }

    @Override
    public void centerX() {
        setLeft(-(width / 2));
    }

    @Override
    public void centerY() {
        setBottom(-(height / 2));
    }

    @Override
    public void center() {
        position.x = -(width / 2);
        position.y = -(height / 2);
        setRect();
    }

    @Override
    public void setLeft(float left) {
        position.x = left;
        setRect();
    }

    @Override
    public void setTop(float top) {
        position.y = top - height;
        setRect();
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
        setRect();
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
        setRect();
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setRect();
    }

    @Override
    public void move(Vector2 delta) {
        position.add(delta);
        setRect();
    }

    /** helper to determine the a collision, world units */
    private void setRect() {
        bounds.setPosition(position.x, position.y);
        bounds.setSize(width, height);
    }
}
