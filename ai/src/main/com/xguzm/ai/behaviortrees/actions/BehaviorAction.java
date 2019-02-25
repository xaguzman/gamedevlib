package com.xguzm.ai.behaviortrees.actions;

import com.xguzm.ai.behaviortrees.BehaviorNode;

public abstract class BehaviorAction<TBlackboard> implements BehaviorNode {
    protected TBlackboard blackboard;
}
