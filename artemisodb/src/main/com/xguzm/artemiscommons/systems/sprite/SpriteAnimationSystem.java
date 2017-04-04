package com.xguzm.artemiscommons.systems.sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.xguzm.artemiscommons.components.Sprite;
import com.xguzm.artemiscommons.components.SpriteAnimator;

/**
 * Created by gdlxguzm on 4/3/2017.
 */
public class SpriteAnimationSystem extends IteratingSystem {

    @Wire SpriteAssetSystem spriteAssets;
    @Wire ComponentMapper<SpriteAnimator> animMapper;
    @Wire ComponentMapper<Sprite> imgMapper;
//    @Wire ComponentMapper<Facing> facingMapper;

    public SpriteAnimationSystem() {
        super(Aspect.all(Sprite.class, SpriteAnimator.class));
    }

    @Override
    protected void process(int entityId) {
        SpriteAnimator animator = animMapper.get(entityId);
        Sprite sprite = imgMapper.get(entityId);

        animator.stateTime += world.delta;
        String animationName = animator.getFullAnimationName();

        Animation anim = spriteAssets.getAnimation(animationName);

        if (anim != null){
            anim.setPlayMode(animator.playMode);
            com.badlogic.gdx.graphics.g2d.Sprite keyFrame = (com.badlogic.gdx.graphics.g2d.Sprite) anim.getKeyFrame(animator.stateTime);
            sprite.asset = keyFrame;
        }else{
            Gdx.app.debug("SpriteAnimationSystem", "Animation '" + animationName + "' not found");
        }

    }
}
