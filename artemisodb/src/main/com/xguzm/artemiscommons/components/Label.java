package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;

/**
 * Created by gdlxguzm on 4/3/2017.
 */
public class Label extends PooledComponent {
    public String fontName;
    public String text;


    @Override
    protected void reset() {
        fontName = null;
        text = null;
    }
}
