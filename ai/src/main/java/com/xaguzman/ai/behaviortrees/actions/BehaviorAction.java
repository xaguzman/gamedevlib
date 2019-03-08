package com.xaguzman.ai.behaviortrees.actions;

import com.xaguzman.ai.behaviortrees.BehaviorNode;

public abstract class BehaviorAction<TBlackboard> implements BehaviorNode {
    protected TBlackboard blackboard;
}
