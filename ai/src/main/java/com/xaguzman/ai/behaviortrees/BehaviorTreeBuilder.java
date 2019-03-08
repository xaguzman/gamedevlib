package com.xaguzman.ai.behaviortrees;

import com.badlogic.gdx.utils.Array;

public final class BehaviorTreeBuilder {

    private BehaviorNode currentNode;
    private final Array<BehaviorNode> parentNodeStack = new Array<BehaviorNode>();

    public final BehaviorTreeBuilder action(String name, BehaviorNode node) throws BehaviorTreeException{
        if (this.parentNodeStack.size <= 0) {
            throw new BehaviorTreeException("Can't create unnested action node: " + name + ". It must be a leaf node");
        } else {
            return this;
        }
    }
}



