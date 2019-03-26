package com.xaguzman.ai.behaviortrees.composites;

import com.badlogic.gdx.utils.Array;
import com.xaguzman.ai.behaviortrees.BehaviorNode;

public abstract class CompositeBehaviorNode implements BehaviorNode {

    public String name;

    public Array<BehaviorNode> children;

    public CompositeBehaviorNode(){
        this(new Array<BehaviorNode>());
    }

    public CompositeBehaviorNode(Array<BehaviorNode> children) {
        this.children = children;
    }

    public CompositeBehaviorNode(BehaviorNode... children){
        this.children = new Array<BehaviorNode>(children);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void add(BehaviorNode node){
        children.add(node);
    }

    @Override
    public void restart() {
        for (BehaviorNode child : children)
            child.restart();
    }
}
