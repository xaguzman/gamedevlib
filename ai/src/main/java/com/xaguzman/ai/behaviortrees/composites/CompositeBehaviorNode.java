package com.xaguzman.ai.behaviortrees.composites;

import com.badlogic.gdx.utils.Array;
import com.xaguzman.ai.behaviortrees.BehaviorNode;

public abstract class CompositeBehaviorNode implements BehaviorNode {

    protected Array<BehaviorNode> children;
    public Array<BehaviorNode> getChildren() {
        return children;
    }

    protected CompositeBehaviorNode(){
        children = new Array<BehaviorNode>();
    }

    protected CompositeBehaviorNode(BehaviorNode... nodes){
        children = new Array<BehaviorNode>(nodes);
    }

    public void add( BehaviorNode node ) {
        children.add(node);
    }

    @Override
    public void restart() {
        for(BehaviorNode child : children) child.restart();
    }
}
