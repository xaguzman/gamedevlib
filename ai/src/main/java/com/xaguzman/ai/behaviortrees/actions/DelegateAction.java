package com.xaguzman.ai.behaviortrees.actions;

import com.xaguzman.ai.behaviortrees.BehaviorNodeResult;
import com.xaguzman.ai.behaviortrees.BehaviorNode;

public class DelegateAction extends BehaviorAction {

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
