package com.xguzm.artemiscommons.components;

import com.artemis.Component;

/**
 * Created by gdlxguzm on 4/4/2017.
 */
public interface Tweenable{
//    /**
//     * Set to tween of two component states. Uses linear tween.
//     *
//     * @param thiz start state at zero.
//     * @param other end state at one.
//     * @param value tween (0..1)
//     * @return {@code this}
//     */
//    void linearTween(T thiz, T other, float value);

    /**
     * Updates [returnValues] with the values of the properties you want to tween
     * when you run a `tweenType` tween.
     * Returns the number of values set in [returnValues].
     */
    void setTweenableValues(float[] newValues);

    /**
     * Updates this object's properties with values from [newValues],
     * in the [tweenType] fashion of `getTweenableValues`.
     */
    void getTweenableValues(float[] returnValues);
}
