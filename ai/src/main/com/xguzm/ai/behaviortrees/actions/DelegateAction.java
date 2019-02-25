package com.xguzm.ai.behaviortrees.actions;

import com.xguzm.ai.behaviortrees.BehaviorNode;
import com.xguzm.ai.behaviortrees.BehaviorNodeResult;
import com.xguzm.commons.Tickable;

public class DelegateAction implements BehaviorNode {

    protected final BehaviorNode delegate;

    public DelegateAction(BehaviorNode action){
        delegate = action;
    }

    @Override
    public BehaviorNodeResult act(float delta) {
        return delegate.act(delta);
    }

    @Override
    public void restart() {
        delegate.restart();
    }

}
