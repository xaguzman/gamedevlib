package com.xaguzman.artemiscommons.components;

/**
 * Created by Xavier Guzman on 4/4/2017.
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
     * Determines the number of values to use from the values array passed to both, {@link #setTweenableValues(float[])} and
     * {@link #getTweenableValues(float[])}
     */
    int getNumComponents();


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
