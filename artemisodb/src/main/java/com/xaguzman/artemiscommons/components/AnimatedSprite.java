package com.xaguzman.artemiscommons.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Composes the animation in the form animationName-actionName-animationNameSuffix,
 */
@PooledWeaver
public class AnimatedSprite extends Component {

    public float stateTime;
    public String currentAnimation = "";
    public boolean loops = false;
    public boolean isAnimationFinished = false;


    public void setTo(String animation){
        if ( ! animation.equals(currentAnimation)){
            currentAnimation = animation;
            stateTime = 0;
            loops = false;
        }
    }

    public void setToLooping(String animation){
        if ( ! animation.equals(currentAnimation)){
            currentAnimation = animation;
            stateTime = 0;
            loops = true;
        }
    }
}
