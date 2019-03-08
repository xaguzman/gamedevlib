package com.xaguzman.artemiscommons.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Interpolation;

public class Tween extends PooledComponent implements Tweener{

    public Interpolation easing;
    public Tweenable tweenable;

    private float[] targetValues;
    private float[] startValues;
    private float[] buffer;
    public boolean isFrom;
    public boolean isStarted, isKilled, isAutoRemove, isPaused, isInitialized;
    public boolean isIterationStep, isFinished, isYoyo;
    public float repeatDelay, deltaTime, currentTime, delay, duration;
    public int step, repeatCnt, repeatCount;

    @Override
    protected void reset() {
        duration = delay = currentTime = deltaTime = repeatDelay = 0 ;
        isYoyo = isFinished = isInitialized = isIterationStep = isKilled = isPaused = isStarted = false;
        isAutoRemove = true;
        easing = null;
        tweenable = null;
    }

    public Tween(){
        reset();
        easing = Interpolation.linear;
        targetValues = new float[2];
        startValues = new float[2];
        buffer = new float[2];
    }

    Tween with(Tweenable target){
        return with(target, 0);
    }

    Tween with(Tweenable target, float duration){
        this.tweenable = target;
        this.duration = duration;
        return this;
    }

    Tween to(float... targetValues){
        if (targetValues.length > this.targetValues.length){
            this.targetValues = new float[targetValues.length];
        }
        System.arraycopy(targetValues,0, this.targetValues, 0, targetValues.length);

        return this;
    }

    Tween from(float... targetValues){
        this.isFrom = true;
        return to(targetValues);
    }

    Tween repeat(int times, float delay){
        repeatCount = times;
        repeatDelay = Math.max(0f, delay);
        isYoyo = false;

        return this;
    }

    Tween repeatYoyo(int times, float delay){
        repeatCount = times;
        repeatDelay = Math.max(0f, delay);
        isYoyo = true;

        return this;
    }

    Tween interpolation(Interpolation interpolation){
        this.easing = interpolation;
        return this;
    }

    public boolean isReverse(){
        return isReverse(this.step);
    }

    public boolean isReverse(int step) {
        return isYoyo && Math.abs(step%4) == 2;
    }

    public boolean isValidCurrentStep(){
        return isValidStep(this.step);
    }

    public boolean isValidStep(int step) {
        return (step >= 0 && step <= repeatCnt*2) || repeatCnt < 0;
    }



    @Override
    public void setup() {
        if (tweenable == null) return;

        if (startValues.length < targetValues.length){
            startValues = new float[targetValues.length];
            buffer = new float[targetValues.length];
        }
        tweenable.getTweenableValues(startValues);

        for (int i=0; i < tweenable.getNumComponents(); i++) {
//            targetValues[i] += isRelative ? startValues[i] : 0;

//            for (int ii=0; ii<waypointsCnt; ii++) {
//                waypoints[ii*combinedAttrsCnt+i] += isRelative ? startValues[i] : 0;
//            }

            if (isFrom) {
                float tmp = startValues[i];
                startValues[i] = targetValues[i];
                targetValues[i] = tmp;
            }
        }
    }

    @Override
    public void update(int step, int lastStep, boolean isIterationStep, float delta) {
        if (tweenable == null || easing == null) return;

        // Case iteration end has been reached

        if (!isIterationStep && step > lastStep) {
            tweenable.setTweenableValues(isReverse(lastStep) ? startValues : targetValues);
            return;
        }

        if (!isIterationStep && step < lastStep) {
            tweenable.setTweenableValues(isReverse(lastStep) ? targetValues : startValues);
            return;
        }

        // Validation
        assert isIterationStep;
        assert currentTime >= 0;
        assert currentTime <= duration;

        // Case duration equals zero
        if (duration < 0.00000000001f && delta > -0.00000000001f) {
            tweenable.setTweenableValues(isReverse(step) ? targetValues : startValues);
            return;
        }

        if (duration < 0.00000000001f && delta < 0.00000000001f) {
            tweenable.setTweenableValues(isReverse(step) ? startValues : targetValues);
            return;
        }

        // Normal behavior

        float time = isReverse(step) ? duration - currentTime : currentTime;
        float t = easing.apply(time / duration);


        for (int i=0; i < tweenable.getNumComponents(); i++) {
            buffer[i] = startValues[i] + t * (targetValues[i] - startValues[i]);
        }

        tweenable.setTweenableValues(buffer);
    }

    @Override
    public void forceEndValues() {
        tweenable.setTweenableValues(targetValues);
    }

    @Override
    public void forceStartValues() {
        tweenable.setTweenableValues(startValues);
    }


}
