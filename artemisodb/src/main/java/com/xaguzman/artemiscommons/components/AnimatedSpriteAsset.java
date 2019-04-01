package com.xaguzman.artemiscommons.components;

import com.artemis.PooledComponent;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.ObjectMap;

@Transient
public class AnimatedSpriteAsset extends PooledComponent {

    public ObjectMap<String, Animation<com.badlogic.gdx.graphics.g2d.Sprite>> animations =
            new ObjectMap<String, Animation<com.badlogic.gdx.graphics.g2d.Sprite>>();

    @Override
    protected void reset() {
        animations.clear();
    }
}
