package com.xguzm.artemiscommons.components.collidershapes;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A rectangle collider, with left, right, top and bottom relative to its position (which is world units)
 * Created by gdlxguzm on 3/28/2017.
 */
public class RectangleCollider extends ColliderShape{

    Rectangle bounds = new Rectangle();


    public RectangleCollider(float width, float height){
        this(width, height, 0, 0);
    }

    public RectangleCollider(float width, float height, float x, float y){
        this(width, height, x, y, 0, 0);
    }

    public RectangleCollider(float width, float height, float x, float y, float left, float bottom){
        this.width = width;
        this.height = height;
        this.left = left;
        this.bottom = bottom;
        position.set(x, y);
    }

    @Override
    protected boolean collidesWith(RectangleCollider rect) {
        return this.bounds.overlaps(rect.bounds);
    }

    @Override
    protected boolean collidesWith(CircleCollider circle) {
        return Intersector.overlaps(circle.bounds, this.bounds);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getLeft() {
        return left;
    }

    @Override
    public float getTop() {
        return bottom + height;
    }

    @Override
    public float getRight(){
        return left + width;
    }

    @Override
    public float getBottom() {
        return bottom;
    }

    @Override
    public void setBottom(float bottom) {
        this.bottom = bottom;
        setRect();
    }

    @Override
    public void setRight(float right) {
        left = right - width;
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
        left = -(width / 2);
        bottom = -(height / 2);
        setRect();
    }

    @Override
    public void setLeft(float left) {
        this.left = left;
        setRect();
    }

    @Override
    public void setTop(float top) {
        bottom = top - height;
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

    /** helper to determine the a collision, world units */
    private void setRect() {
        bounds.setPosition(position.x + left, position.y + bottom);
        bounds.setSize(width, height);
    }
}
