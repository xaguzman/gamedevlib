package com.xaguzman.artemiscommons.components.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * Created by gdlxguzm on 3/31/2017.
 */
public class Origin extends PooledComponent {
    public final Vector2 xy = new Vector2();

    /** The default origin, 0,0 */
    public static final Origin BOTTOM_LEFT = new Origin();

    /**
     * Origin at 0.5, 0.5
     */
    public static final Origin CENTER = new Origin(0.5f, 0.5f);

    /**
     * Origin at 1,0
     */
    public static final Origin TOP_LEFT = new Origin(0, 1);

    public Origin(){}

    public Origin(float x, float y){
        xy.set(x, y);
    }

    @Override
    protected void reset() {
        xy.setZero();
    }
}
