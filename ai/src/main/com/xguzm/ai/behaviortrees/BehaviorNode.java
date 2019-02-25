package com.xguzm.ai.behaviortrees;

public interface BehaviorNode {
     BehaviorNodeResult act(float delta);

    void restart();
}
