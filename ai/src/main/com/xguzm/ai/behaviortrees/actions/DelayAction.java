package com.xguzm.ai.behaviortrees.actions;

import com.xguzm.ai.behaviortrees.BehaviorNode;
import com.xguzm.ai.behaviortrees.BehaviorNodeResult;

public class DelayAction implements BehaviorNode {

    protected float timeElapsed = 0f;
    protected float timeToWait;

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
