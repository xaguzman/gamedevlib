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

    /**
     * Stores the animation names (as in a TextureAtlas animation) and keys them by an alias.
     */
    private ObjectMap<String, String> animations = new ObjectMap<String, String>();

    /**
     * Wether each animation is a looping animation or not.
     */
    private ObjectMap<String, Boolean> loops = new ObjectMap<String, Boolean>();

    public float stateTime;
    public String currentAnimation = "";

    public void add(String name, String atlasAnimation){
        animations.put(name, atlasAnimation);
    }

    public void setTo(String animation){
        if ( ! animation.equals(currentAnimation)){
            currentAnimation = animation;
            stateTime = 0;
        }
    }

    public void loops(String alias, boolean loops){
        this.loops.put(alias, loops);
    }

    public void remove(String name){
        animations.remove(name);
        loops.remove(name);
    }

    public boolean isLoop(String name){
        return loops.get(name, false);
    }

}
