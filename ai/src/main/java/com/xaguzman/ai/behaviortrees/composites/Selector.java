package com.xaguzman.ai.behaviortrees.composites;

import com.xaguzman.ai.behaviortrees.BehaviorNodeResult;

/**
 * Executes the given list of  [BehaviorNode] in order from 0 to N. Once one of them returns [BehaviorResult.Success] or [BehaviorResult.Running]
 * it stops checking its child nodes. Make sure that the lower index nodes have heavier requirements to be fullfilled, so the next nodes have a good chance of
 * being executed.

 * @author Xavier Guzman
 */
public class Selector extends CompositeBehaviorNode {

    protected int lastRunningIndex = 0;

    @Override
    public BehaviorNodeResult act(float delta) {
        BehaviorNodeResult result = BehaviorNodeResult.Failure;

        int currentIndex = 0;
        while (currentIndex < children.size && result == BehaviorNodeResult.Failure) {
            result = children.get(currentIndex++).act(delta);
        }

        if (lastRunningIndex > currentIndex)
            children.get(lastRunningIndex).restart();

        lastRunningIndex = Math.min(children.size - 1, currentIndex);
        for (int i = currentIndex - 2; i >= 0; i--) {
            children.get(i).restart();
        }
        return result;
    }

    @Override
    public void restart() {
        super.restart();
        lastRunningIndex = 0;
    }
}
