package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;
import com.xguzm.artemiscommons.systems.sprite.SpriteAnimationSystem;
import com.xguzm.artemiscommons.systems.sprite.SpriteAssetManager;
import com.xguzm.artemiscommons.systems.sprite.SpriteAssetSystem;

/**
 *
 * Created by xavier  on 4/3/2017.
 */
public class Sprite extends PooledComponent {
    /** The name or id of this sprite. If not set, a {@link SpriteAnimator } should be set so the {@link #asset} can be retrieved  */
    public String name;

    /** Do not set manually, let the {@link SpriteAssetManager} or the {@link SpriteAnimationSystem}
     * set it. if an {@link SpriteAnimator} is added to the parent entity, the animation system will set this value. Otherwise, the value
     * will be set by retrieving it form the {@link SpriteAssetSystem} by the SpriteAssetManager
     * */
    public com.badlogic.gdx.graphics.g2d.Sprite asset;

    /**  The layer to draw the sprite, lower values go at the back */
    public int layer;

    @Override
    protected void reset() {
        name = "";
        layer = 0;
        asset = null;
    }
}
