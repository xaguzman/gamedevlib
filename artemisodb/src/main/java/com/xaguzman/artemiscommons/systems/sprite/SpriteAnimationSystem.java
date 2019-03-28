package com.xaguzman.artemiscommons.systems.sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.xaguzman.artemiscommons.components.Sprite;
import com.xaguzman.artemiscommons.components.SpriteAnimator;
import com.xaguzman.artemiscommons.components.SpriteAsset;

/**
 * Created by gdlxguzm on 4/3/2017.
 */
public class SpriteAnimationSystem extends IteratingSystem {

    @Wire SpriteAssetSystem spriteAssets;
    @Wire ComponentMapper<SpriteAnimator> animMapper;
    @Wire ComponentMapper<SpriteAsset> assetMapper;

    public SpriteAnimationSystem() {
        super(Aspect.all(SpriteAnimator.class, SpriteAsset.class));
    }

    @Override
    protected void process(int entityId) {
        SpriteAnimator animator = animMapper.get(entityId);
        SpriteAsset asset = assetMapper.get(entityId);

        animator.stateTime += world.delta;
        String animationName = animator.getFullAnimationName();

        Animation<com.badlogic.gdx.graphics.g2d.Sprite> anim = spriteAssets.getAnimation(animationName);

        if (anim != null){
            anim.setPlayMode(animator.playMode);
            com.badlogic.gdx.graphics.g2d.Sprite keyFrame = (com.badlogic.gdx.graphics.g2d.Sprite) anim.getKeyFrame(animator.stateTime);
            asset.asset = keyFrame;
        }else{
            Gdx.app.debug("SpriteAnimationSystem", "Animation '" + animationName + "' not found");
        }

    }
}
