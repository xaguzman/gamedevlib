package com.xaguzman.ai.behaviortrees.actions;

import com.xaguzman.ai.behaviortrees.BehaviorNode;

public abstract class BehaviorAction implements BehaviorNode {
    protected String name;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
