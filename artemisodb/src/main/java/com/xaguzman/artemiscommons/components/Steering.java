package com.xaguzman.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

public class Steering extends PooledComponent {
    public Vector2 force = new Vector2();
    public float mass = 1.5f;

    @Override
    protected void reset() {
        force.setZero();
        mass = 1.5f;
    }
}
