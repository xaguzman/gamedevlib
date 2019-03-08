package com.xaguzman.ai.behaviortrees.decorators;

import com.xaguzman.ai.behaviortrees.BehaviorNodeResult;
import com.xaguzman.ai.behaviortrees.BehaviorNode;

/**
 * Decorator to add a delay time to the execution of a [node][Behavior]. The given [node][Behavior] will not be executed
 * until the delayTime has totally elapsed.
 * @author Xavier Guzman
 */
public class DelayDecorator extends BehaviorNodeDecorator{
    private float delayLeft = 0f;
    public float delayTime = 0f;

    public DelayDecorator(BehaviorNode target, float time){
        super(target);
        this.delayTime = time;
        this.delayLeft = time;
    }


    @Override
    public BehaviorNodeResult act(float delta) {
        delayLeft -= delta;
        return delayLeft > 0 ? BehaviorNodeResult.Running : target.act(delta);
    }

    @Override
    public void restart() {
        delayLeft = delayTime;
    }
}
