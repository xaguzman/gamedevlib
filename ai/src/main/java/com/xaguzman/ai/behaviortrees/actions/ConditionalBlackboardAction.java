package com.xaguzman.ai.behaviortrees.actions;

import com.xaguzman.ai.behaviortrees.BehaviorNodeResult;

/**
 * Created by Xavier Guzman on 5/11/2017.
 */
public abstract class ConditionalBlackboardAction<T> extends ConditionalBehaviorAction {
    protected T blackboard;

    public void setBlackboard(T blackboard){
        this.blackboard = blackboard;
    }

    public T getBlackboard() {
        return blackboard;
    }
}
