package com.xaguzman.ai.behaviortrees.decorators;

import com.xaguzman.ai.behaviortrees.BehaviorNode;

public abstract class BehaviorNodeDecorator implements BehaviorNode {
    protected BehaviorNode target;

    public BehaviorNodeDecorator(BehaviorNode target){
        this.target = target;
    }
}
