package com.xguzm.ai.behaviortrees;

import com.badlogic.gdx.utils.Array;
import com.sun.istack.internal.NotNull;

public final class BehaviorTreeBuilder {

    private com.xguzm.ai.behaviortrees.BehaviorNode currentNode;
    private final Array<BehaviorNode> parentNodeStack = new Array<BehaviorNode>();

    @NotNull
    public final BehaviorTreeBuilder action(@NotNull String name, @NotNull BehaviorNode node) throws BehaviorTreeException{
        if (this.parentNodeStack.size <= 0) {
            throw new BehaviorTreeException("Can't create unnested action node: " + name + ". It must be a leaf node");
        } else {
            return this;
        }
    }
}



