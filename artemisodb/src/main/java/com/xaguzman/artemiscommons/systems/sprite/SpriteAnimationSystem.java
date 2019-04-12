package com.xaguzman.artemiscommons.systems.sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.xaguzman.artemiscommons.components.AnimatedSprite;
import com.xaguzman.artemiscommons.components.SpriteAsset;

/**
 * Created by Xavier Guzman on 4/3/2017.
 */
public class SpriteAnimationSystem extends IteratingSystem {

    @Wire SpriteAssetSystem spriteAssets;
    @Wire ComponentMapper<AnimatedSprite> animMapper;
    @Wire ComponentMapper<SpriteAsset> assetMapper;

    public SpriteAnimationSystem() {
        super(Aspect.all(AnimatedSprite.class, SpriteAsset.class));
    }

    @Override
    protected void process(int entityId) {
        AnimatedSprite animator = animMapper.get(entityId);
        SpriteAsset asset = assetMapper.get(entityId);

        animator.stateTime += world.delta;
        String animationName = animator.currentAnimation;

        Animation<com.badlogic.gdx.graphics.g2d.Sprite> anim = spriteAssets.getAnimation(animationName);

        if (anim != null){
            asset.asset = anim.getKeyFrame(animator.stateTime, animator.loops);
            animator.isAnimationFinished = !animator.loops && anim.isAnimationFinished(animator.stateTime);
        }else{
            Gdx.app.debug("SpriteAnimationSystem", "Animation '" + animationName + "' not found");
        }

    }
}
