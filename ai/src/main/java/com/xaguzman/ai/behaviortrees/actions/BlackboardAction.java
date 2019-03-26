package com.xaguzman.ai.behaviortrees.actions;

/**
 * A leaf action with access to a blackboard to make decisions
 */
public abstract class BlackboardAction<T> extends BehaviorAction {

    protected T blackboard;

    public void setBlackboard(T blackboard){
        this.blackboard = blackboard;
    }

    public T getBlackboard() {
        return blackboard;
    }
}
