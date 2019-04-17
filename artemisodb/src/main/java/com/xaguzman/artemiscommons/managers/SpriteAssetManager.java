package com.xaguzman.artemiscommons.managers;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.xaguzman.artemiscommons.components.AnimatedSprite;
import com.xaguzman.artemiscommons.components.Sprite;
import com.xaguzman.artemiscommons.components.SpriteAsset;
import com.xaguzman.artemiscommons.components.transform.Size;
import com.xaguzman.artemiscommons.managers.AssetManager;
import com.xaguzman.artemiscommons.managers.DualAssetManager;
import com.xaguzman.artemiscommons.systems.base.PassiveSystem;
import com.xaguzman.artemiscommons.systems.sprite.SpriteAssetSystem;

import static com.artemis.Aspect.all;

/**
 * Sets sprites and animated sprites to have an actual sprite asset when inserted.
 * Created by Xavier Guzman on 4/3/2017.
 */
public class SpriteAssetManager extends DualAssetManager<Sprite, AnimatedSprite, SpriteAsset> {

    @Wire SpriteAssetSystem spriteAssets;

    public SpriteAssetManager() {
        super(Sprite.class, AnimatedSprite.class, SpriteAsset.class);
    }

    @Override
    protected void setupMeta1(int entityId, Sprite sprite, SpriteAsset spriteAsset) {
        spriteAsset.asset = spriteAssets.getSprite(sprite.name);
        spriteAsset.layer = sprite.layer;
        spriteAsset.offsetX = sprite.offsetX;
        spriteAsset.offsetY = sprite.offsetY;
    }

    @Override
    protected void setupMeta2(int entityId, AnimatedSprite animatedSprite, SpriteAsset spriteAsset) {
        Animation<com.badlogic.gdx.graphics.g2d.Sprite> anim  = spriteAssets.getAnimation(animatedSprite.currentAnimation);
        spriteAsset.asset = anim.getKeyFrame(animatedSprite.stateTime, animatedSprite.loops);
        spriteAsset.layer = animatedSprite.layer;
        spriteAsset.offsetX = animatedSprite.offsetX;
        spriteAsset.offsetY = animatedSprite.offsetY;
    }




}
