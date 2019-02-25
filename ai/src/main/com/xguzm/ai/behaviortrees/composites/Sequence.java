package com.xguzm.ai.behaviortrees.composites;

import com.xguzm.ai.behaviortrees.BehaviorNodeResult;

/**
 * Keeps executing its children while they return Success. Once one returns Failure or Running, it stops.
 * If Running is returned, on next iteration  it will start from the child that returned Running
 */
public class Sequence extends CompositeBehaviorNode {

    protected int lastRunningIndex = 0;

    @Override
    public BehaviorNodeResult act(float delta) {
        int currentIndex = lastRunningIndex;
        while (currentIndex < children.size) {

            BehaviorNodeResult childResult = children.get(currentIndex).act(delta);
            if (childResult == BehaviorNodeResult.Failure) {
                return BehaviorNodeResult.Failure;
            }
            if (childResult == BehaviorNodeResult.Running) {
                lastRunningIndex = currentIndex;
                return BehaviorNodeResult.Running;
            }
            currentIndex++;
        }

        restart(); //sequence succeeded, restart for next time the sequence is needed
        return BehaviorNodeResult.Success;
    }

    @Override
    public void restart() {
        super.restart();
        lastRunningIndex = 0;
    }
}
