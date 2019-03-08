package com.xaguzman.ai.behaviortrees.actions;

import com.xaguzman.ai.behaviortrees.BehaviorNodeResult;


/**
 * A node that will only ever return either [BehaviorResult.Success] or [BehaviorResult.Failure] given a condition
 * @author Xavier
 */
public abstract class ConditionalAction<T> extends BehaviorAction<T> {

    @Override
    public BehaviorNodeResult act(float delta) {
        BehaviorNodeResult result;

        result = conditionMet() ? BehaviorNodeResult.Success : BehaviorNodeResult.Failure;

        return result;
    }

    @Override
    public void restart() {

    }

    public abstract boolean conditionMet();
}
