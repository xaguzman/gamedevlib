package com.xguzm.ai.behaviortrees.decorators;

import com.xguzm.ai.behaviortrees.BehaviorNode;

public abstract class BehaviorNodeDecorator implements BehaviorNode {
    protected BehaviorNode target;

    public BehaviorNodeDecorator(BehaviorNode target){
        this.target = target;
    }
}
