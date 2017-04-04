package com.xguzm.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Composes the animation in the form animationName-actionName-animationNameSuffix,
 *
 */
public class SpriteAnimator extends PooledComponent {

    private static final StringBuilder builder = new StringBuilder();

    public float stateTime;
    public Animation.PlayMode playMode;
    private String animationName;
    private String actionName;
    private String animationNameSuffix;

    public SpriteAnimator(){
        playMode = Animation.PlayMode.NORMAL;
    }

    public void setAnimationName(String name){
        if(name.equals(animationName))
            return;
        animationName = name;
        stateTime = 0;
    }

    public void setAnimationNameSuffix(String name){
        if (name.equals(animationNameSuffix))
            return;

        animationNameSuffix = name;
        stateTime = 0;
    }

    public void setActionName(String action){
        if(action.equals(actionName))
            return;
        actionName = action;
        stateTime = 0;
    }

    public String getAnimationName(){
        return animationName;
    }

    public String getActionName(){
        return actionName;
    }

    public String getAnimationNameSuffix() { return animationNameSuffix; }

    @Override
    protected void reset() {
        stateTime = 0;
        animationName = actionName = "";
        playMode = Animation.PlayMode.NORMAL;
    }

//    public String getFullAnimationName(Facing facing){
//        builder.setLength(0);
//        builder.append(animationName);
//
//        if (animationNameSuffix != null && animationNameSuffix.length() > 0){
//            builder.append("-").append(animationNameSuffix);
//        }
//
//        if (actionName != null && actionName.length() > 0){
//            builder.append("-").append(actionName);
//        }
//
//        if (facing != null){
//            builder.append("-").append(facing.direction);
//        }
//
//        return builder.toString();
//    }

    public String getFullAnimationName(){
        builder.setLength(0);
        builder.append(animationName);

        if (animationNameSuffix != null && animationNameSuffix.length() > 0){
            builder.append("-").append(animationNameSuffix);
        }

        if (actionName != null && actionName.length() > 0){
            builder.append("-").append(actionName);
        }

//        if (facing != null){
//            builder.append("-").append(facing.direction);
//        }

        return builder.toString();
    }
}
