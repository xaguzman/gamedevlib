package com.xaguzman.artemiscommons.components.collidershapes;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

/**
 * Collider with circle shape, where it's xy is absolute (world xy).
 *
 * It has a radius, and the middle of the radius is given by left and bottom properties.
 * Created by Xavier Guzman on 3/28/2017.
 */
public class CircleCollider extends ColliderShape{

    protected Circle bounds = new Circle();
    private float radius;

    public CircleCollider(float radius){
        this(radius, 0, 0);
    }

    public CircleCollider(float radius, float x, float y){
        this.radius = radius;
        position.set(x, y);
    }

    @Override
    public int getShapeType() {
        return ColliderShape.TYPE_CIRCLE;
    }

    public float getRadius(){
        return radius;
    }

    public void setRadius(float newRadius){
        radius = newRadius;
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
        return position.y + radius;
    }

    @Override
    public void setTop(float top) {
        position.y = top - radius;
        setCircle();
    }

    @Override
    public float getBottom() {
        return position.y;
    }

    @Override
    public void setBottom(float bottom) {
        position.y = bottom + radius;
        setCircle();
    }

    @Override
    public float getLeft() {
        return position.x - radius;
    }

    @Override
    public void setLeft(float left) {
        position.x = left + radius;
        setCircle();
    }

    @Override
    public float getRight() {
        return position.x + radius;
    }

    @Override
    public void setRight(float right) {
        position.x = right - radius;
        setCircle();
    }

    @Override
    public void centerX() {
//        setLeft(-radius);
//        setCircle();
    }

    @Override
    public void centerY() {
//        setBottom(-radius);
//        setCircle();
    }

    @Override
    public void center() {
//        position.x = -radius;
//        bottom = -radius;
//        setCircle();
    }

    @Override
    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setCircle();
    }

    @Override
    protected void moved(){
        setCircle();
    }

    private void setCircle(){
        bounds.setPosition( position.x, position.y);
        bounds.setRadius(radius);
    }

}
