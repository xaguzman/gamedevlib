package com.xaguzman.artemiscommons.components;

import com.artemis.PooledComponent;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

@Transient
public class BitmapFontAsset extends PooledComponent {

    public BitmapFont font;

    @Override
    protected void reset() {
        font = null;
    }
}
