package com.xaguzman.ai.behaviortrees.composites;

import com.xaguzman.ai.behaviortrees.BehaviorNodeResult;
import com.xaguzman.ai.behaviortrees.BehaviorNode;

/**
 * Executes all of its children everytime. If the minimum ammount of successes are returned by the children, this one will return
 * Success. If the minimum ammount of Failures are returned by the children, this will return Failures. Otherwise it will
 * return Running.

 * Priority is put on success rather than failures (meaning that if both, the @{link requiredSuccess} and @{link requiredFailures} are met, it will
 * still return Success)
 */
public class Parallel extends CompositeBehaviorNode {

    /**
     * The number of successes required to consider an iteration a success
     */
    public int requiredSuccess = -1;

    /**
     * The number of failures required to consider an iteration a failure
     */
    public int requiredFailures = -1;


    @Override
    public BehaviorNodeResult act(float delta) {
        if (requiredSuccess == -1)
            requiredSuccess = children.size;

        if (requiredFailures == -1)
            requiredFailures = children.size;

        int countSuccess = 0;
        int countFailres = 0;
        for (BehaviorNode child : children) {
            BehaviorNodeResult childResult = child.act(delta);
            if (childResult == BehaviorNodeResult.Success)
                countSuccess++;
            else if (childResult == BehaviorNodeResult.Failure)
                countFailres++;
        }

        if (countSuccess >= requiredSuccess)
            return BehaviorNodeResult.Success;

        if (countFailres >= requiredFailures)
            return BehaviorNodeResult.Failure;

        return BehaviorNodeResult.Running;
    }

    @Override
    public void restart() {
//        super.restart();
    }
}
