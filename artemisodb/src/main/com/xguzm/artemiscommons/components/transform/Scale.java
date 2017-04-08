package com.xguzm.artemiscommons.components.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;
import com.xguzm.artemiscommons.components.Tweenable;

/**
 * Created by gdlxguzm on 3/31/2017.
 */
public class Scale extends PooledComponent implements Tweenable{
    public final Vector2 xy = new Vector2(1,1);

    public static final Scale DEFAULT = new Scale();

    public float x(){
        return xy.y;
    }

    public float y(){
        return xy.y;
    }

    @Override
    protected void reset() {
        xy.set(1,1);
    }

    @Override
    public int getNumComponents() {
        return 2;
    }

    @Override
    public void setTweenableValues(float[] newValues) {
        xy.set(newValues[0], newValues[1]);
    }

    @Override
    public void getTweenableValues(float[] returnValues) {
        returnValues[0] = x();
        returnValues[1] = y();
    }
}
