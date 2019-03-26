package com.xaguzman.ai.behaviortrees.actions;

import com.xaguzman.ai.behaviortrees.BehaviorNodeResult;

/**
 * Created by gdlxguzm on 5/11/2017.
 */
public abstract class ConditionalBlackboardAction<T> extends BlackboardAction<T> {
    @Override
    public BehaviorNodeResult act(float delta) {
        BehaviorNodeResult result = conditionMet() ? BehaviorNodeResult.Success : BehaviorNodeResult.Failure;

        return result;
    }

    @Override
    public void restart() {

    }

    public abstract boolean conditionMet();
}
