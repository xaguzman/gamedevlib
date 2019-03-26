package com.xaguzman.ai.behaviortrees.decorators;

import com.xaguzman.ai.behaviortrees.BehaviorNode;

public abstract class BehaviorNodeDecorator implements BehaviorNode {

    protected BehaviorNode target;
    protected String name;

    public BehaviorNodeDecorator(BehaviorNode target) {
        this.target = target;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
