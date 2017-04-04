package com.xguzm.artemiscommons.managers;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.xguzm.artemiscommons.components.Tween;

/**
 * References the right component once a new tween is added
 * Created by gdlxguzm on 4/4/2017.
 */
public class TweenManager extends IteratingSystem{

    @Wire ComponentMapper<Tween> tweenMapper;

    /**
     * Creates an entity system that uses the specified aspect as a matcher
     * against entities.
     *
     */
    public TweenManager() {
        super(Aspect.all(Tween.class));
    }


    @Override
    protected void process(int entityId) {
//        Tween tween = tweenMapper.get
//        if (tween.getType() == null){
//            Gdx.app.debug("TweenManager", "No type given for the tween. Tween will be removed");
//        }

//        Tweenable<?> tweenableComponent = world.getEntity(entityId).getComponent(tween.getType());
    }
}
