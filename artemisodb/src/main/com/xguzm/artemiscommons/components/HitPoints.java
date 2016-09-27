package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;

/**
 * Created by gdlxguzm on 3/21/2015.
 */
public class HitPoints extends PooledComponent {
    public int max, current;

    @Override
    protected void reset() {
        max = current = 0;
    }

    public void setAll(int value){
        max = current = value;
    }

}
