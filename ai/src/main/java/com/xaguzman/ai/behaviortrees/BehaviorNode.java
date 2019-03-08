package com.xaguzman.ai.behaviortrees;

public interface BehaviorNode {
     BehaviorNodeResult act(float delta);

    void restart();
}
