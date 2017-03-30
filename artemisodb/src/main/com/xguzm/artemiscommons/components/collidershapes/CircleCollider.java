package com.xguzm.artemiscommons.components.collidershapes;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

/**
 * Collider with circle shape, where it's position is absolute (world position).
 *
 * It has a radius, and the middle of the radius is given by left and bottom properties.
 * Created by gdlxguzm on 3/28/2017.
 */
public class CircleCollider extends ColliderShape{

    Circle bounds = new Circle();
    private float radius;

    public CircleCollider(float radius){
        this(radius, 0, 0);
    }

    public CircleCollider(float radius, float x, float y){

    }

    public CircleCollider(float radius, float x, float y, float left, float bottom){
        this.radius = radius;
        this.left = left;
        this.bottom = bottom;
        position.set(x, y);
    }


    @Override
    public boolean collidesWith(RectangleCollider rect) {
        return Intersector.overlaps(this.bounds, rect.bounds);
    }

    @Override
    public boolean collidesWith(CircleCollider circle) {
        return bounds.overlaps(circle.bounds);
    }

    @Override
    public float getWidth() {
        return radius * 2;
    }

    @Override
    public void setWidth(float width) {
        radius = width / 2;
        setCircle();
    }

    @Override
    public float getHeight() {
        return radius * 2;
    }

    @Override
    public void setHeight(float height) {
        radius = height / 2;
        setCircle();
    }

    @Override
    public float getTop() {
        return bottom + radius;
    }

    @Override
    public void setTop(float top) {
        bottom = top - radius;
        setCircle();
    }

    @Override
    public float getBottom() {
        return bottom;
    }

    @Override
    public void setBottom(float bottom) {
        this.bottom = bottom;
        setCircle();
    }

    @Override
    public float getLeft() {
        return left;
    }

    @Override
    public void setLeft(float left) {
        this.left = left;
        setCircle();
    }

    @Override
    public float getRight() {
        return left + radius;
    }

    @Override
    public void setRight(float right) {
        this.left = right - radius;
        setCircle();
    }

    @Override
    public void centerX() {
        setLeft(-radius);
        setCircle();
    }

    @Override
    public void centerY() {
        setBottom(-radius);
        setCircle();
    }

    @Override
    public void center() {
        left = -radius;
        bottom = -radius;
        setCircle();
    }

    private void setCircle(){
        bounds.setPosition( position.x + left, position.y + bottom);
        bounds.setRadius(radius);
    }

}
