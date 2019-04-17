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


    public float offsetX = 0f;
    public float offsetY = 0f;

    /**  The layer to draw the sprite, lower values go at the back. Default is 0 */
    public int layer;

    public void setTo(String animation){
        setTo(animation, false);
    }

    public void setToLooping(String animation){
        setTo(animation, true);
    }

    public void setTo(String animation, boolean looping){
        if ( ! animation.equals(currentAnimation)){
            currentAnimation = animation;
            stateTime = 0;
            loops = looping;
        }
    }
}
