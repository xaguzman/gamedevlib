package com.xaguzman.ai.behaviortrees.actions;

import com.xaguzman.ai.behaviortrees.BehaviorNodeResult;
import com.xaguzman.ai.behaviortrees.BehaviorNode;

public class DelayAction extends BehaviorAction {

    protected float timeElapsed = 0f;
    public float timeToWait;

    public DelayAction(float delayDuration){
        timeToWait = delayDuration;
    }

    @Override
    public BehaviorNodeResult act(float delta) {
        if (timeToWait < 0f)
            return BehaviorNodeResult.Failure;

        timeElapsed += delta;
        if (timeElapsed <= timeToWait)
            return BehaviorNodeResult.Running;

        return BehaviorNodeResult.Success;
    }

    @Override
    public void restart() {
        timeElapsed = 0f;
    }
}
