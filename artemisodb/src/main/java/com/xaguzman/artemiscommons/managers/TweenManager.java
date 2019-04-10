package com.xaguzman.artemiscommons.managers;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.xaguzman.artemiscommons.components.Tween;

/**
 * References the right component once a new tween is added
 * Created by Xavier Guzman on 4/4/2017.
 */
public class TweenManager extends BaseEntitySystem{

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
    protected void processSystem() {
        IntBag actives = subscription.getEntities();
        int[] ids = actives.getData();

        if (world.getDelta() >= 0) {
            for (int i = 0; i < actives.size(); i++) {
                process(ids[i]);
            }
        } else {
            for (int i = actives.size() - 1; i >=0 ; i--) {
                process(ids[i]);
            }
        }
    }

    protected void process(int entityId) {
        Tween tween = tweenMapper.get(entityId);
        if (tween.isFinished && tween.isAutoRemove){
            tweenMapper.remove(entityId);
            return;
        }

        if (!tween.isStarted || tween.isPaused || tween.isKilled) return;

        tween.deltaTime = world.getDelta();

        if (!tween.isInitialized) {
            initialize(tween);
        }

        if (tween.isInitialized) {
            testRelaunch(tween);
            updateStep(tween);
            testCompletion(tween);
        }

        tween.currentTime += tween.deltaTime;
//        tween.deltaTime = 0;
    }

    private void initialize(Tween tween) {
        if (tween.currentTime+ tween.deltaTime >= tween.delay) {
            tween.setup();
            tween.isInitialized = true;
            tween.isIterationStep = true;
            tween.step = 0;
            tween.deltaTime -= tween.delay - tween.currentTime;
            tween.currentTime = 0;
        }
    }

    private void testRelaunch(Tween tween) {
        if (!tween.isIterationStep && tween.repeatCnt >= 0 && tween.step < 0 && tween.currentTime +
                tween.deltaTime >= 0) {
            assert tween.step == -1;
            tween.isIterationStep = true;
            tween.step = 0;
            float delta = 0 - tween.currentTime;
            tween.deltaTime -= delta;
            tween.currentTime = 0;
//            callCallback(TweenCallback.BEGIN);
//            callCallback(TweenCallback.START);
            tween.update(tween.step, tween.step-1, tween.isIterationStep, delta);

        } else if (!tween.isIterationStep &&
                tween.repeatCnt >= 0 &&
                tween.step > tween.repeatCnt*2 && tween.currentTime+tween.deltaTime < 0) {
            assert tween.step == tween.repeatCnt*2 + 1;
            tween.isIterationStep = true;
            tween.step = tween.repeatCnt*2;
            float delta = 0-tween.currentTime;
            tween.deltaTime -= delta;
            tween.currentTime = tween.duration;
//            callCallback(TweenCallback.BACK_BEGIN);
//            callCallback(TweenCallback.BACK_START);
            tween.update(tween.step, tween.step+1, tween.isIterationStep, delta);
        }
    }

    private void updateStep(Tween tween){
        while (tween.isValidCurrentStep()) {
            if (!tween.isIterationStep && tween.currentTime + tween.deltaTime <= 0) {
                tween.isIterationStep = true;
                tween.step -= 1;

                float delta = 0 - tween.currentTime;
                tween.deltaTime -= delta;
                tween.currentTime = tween.duration;

                if (tween.isReverse())
                    tween.forceStartValues();
                else
                    tween.forceEndValues();
//                callCallback(TweenCallback.BACK_START);
                tween.update(tween.step, tween.step+1, tween.isIterationStep, delta);

            } else if (!tween.isIterationStep && tween.currentTime+ tween.deltaTime >= tween.repeatDelay) {
                tween.isIterationStep = true;
                tween.step += 1;

                float delta = tween.repeatDelay-tween.currentTime;
                tween.deltaTime -= delta;
                tween.currentTime = 0;

                if (tween.isReverse())
                    tween.forceEndValues();
                else
                    tween.forceStartValues();
//                callCallback(TweenCallback.START);
                tween.update(tween.step, tween.step-1, tween.isIterationStep, delta);

            } else if (tween.isIterationStep && tween.currentTime+tween.deltaTime < 0) {
                tween.isIterationStep = false;
                tween.step -= 1;

                float delta = 0-tween.currentTime;
                tween.deltaTime -= delta;
                tween.currentTime = 0;

                tween.update(tween.step, tween.step+1, tween.isIterationStep, delta);
//                callCallback(TweenCallback.BACK_END);

//                if (step < 0 && repeatCnt >= 0)
//                    callCallback(TweenCallback.BACK_COMPLETE);
//                else
                tween.currentTime = tween.repeatDelay;

            } else if (tween.isIterationStep && tween.currentTime+tween.deltaTime > tween.duration) {
                tween.isIterationStep = false;
                tween.step += 1;

                float delta = tween.duration-tween.currentTime;
                tween.deltaTime -= delta;
                tween.currentTime = tween.duration;

                tween.update(tween.step, tween.step-1, tween.isIterationStep, delta);
//                callCallback(TweenCallback.END);

//                if (tween.step > tween.repeatCnt*2 && tween.repeatCnt >= 0)
//                    callCallback(TweenCallback.COMPLETE);
                tween.currentTime = 0;

            } else if (tween.isIterationStep) {
                float delta = tween.deltaTime;
                tween.deltaTime -= delta;
                tween.currentTime += delta;
                tween.update(tween.step, tween.step, tween.isIterationStep, delta);
                break;

            } else {
                float delta = tween.deltaTime;
                tween.deltaTime -= delta;
                tween.currentTime += delta;
                break;
            }
        }
    }



    private void testCompletion(Tween tween) {
        tween.isFinished = tween.repeatCnt >= 0 && (tween.step > tween.repeatCnt*2 || tween.step < 0);
    }
}
