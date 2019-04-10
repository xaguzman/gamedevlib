package com.xaguzman.artemiscommons.systems.sprite;

import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.xaguzman.artemiscommons.components.AnimatedSprite;
import com.xaguzman.artemiscommons.components.SpriteAsset;
import com.xaguzman.artemiscommons.managers.AssetManager;

public class AnimatedSpriteAssetManager extends AssetManager<AnimatedSprite, SpriteAsset> {

    @Wire SpriteAssetSystem spriteAssets;

    public AnimatedSpriteAssetManager() {
        super(AnimatedSprite.class, SpriteAsset.class);
    }

    @Override
    protected void setup(int entityId, AnimatedSprite animatedSprite, SpriteAsset spriteAsset) {
        Animation<Sprite>  anim  = spriteAssets.getAnimation(animatedSprite.currentAnimation);
        spriteAsset.asset = anim.getKeyFrame(animatedSprite.stateTime, animatedSprite.loops);
    }
}
