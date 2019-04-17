package com.xaguzman.artemiscommons.components;

import com.artemis.Component;
import com.artemis.annotations.Transient;
import com.xaguzman.artemiscommons.systems.sprite.SpriteAnimationSystem;
import com.xaguzman.artemiscommons.managers.SpriteAssetManager;
import com.xaguzman.artemiscommons.systems.sprite.SpriteAssetSystem;

@Transient
public class SpriteAsset extends Component {
    /** Do not set manually, let the {@link SpriteAssetManager} or the {@link SpriteAnimationSystem}
     * set it. if an {@link AnimatedSprite} is added to the parent entity, the animation system will set this value. Otherwise, the value
     * will be set by retrieving it form the {@link SpriteAssetSystem} by the SpriteAssetManager
     * */
    public com.badlogic.gdx.graphics.g2d.Sprite asset;
    public int layer;
    public float offsetX = 0f;
    public float offsetY = 0f;

}
