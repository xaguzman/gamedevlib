package com.xguzm.artemiscommons.components.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * Created by gdlxguzm on 3/31/2017.
 */
public class Origin extends PooledComponent {
    public final Vector2 xy = new Vector2();

    /** The default origin, 0,0 */
    public static final Origin DEFAULT = new Origin();

    @Override
    protected void reset() {
        xy.setZero();
    }
}
