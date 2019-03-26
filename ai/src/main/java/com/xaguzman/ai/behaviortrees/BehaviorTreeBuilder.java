package com.xaguzman.ai.behaviortrees;

import com.badlogic.gdx.utils.Array;
import com.xaguzman.ai.behaviortrees.actions.BehaviorAction;
import com.xaguzman.ai.behaviortrees.actions.ConditionalBehaviorAction;
import com.xaguzman.ai.behaviortrees.actions.DelayAction;
import com.xaguzman.ai.behaviortrees.composites.CompositeBehaviorNode;
import com.xaguzman.ai.behaviortrees.composites.Parallel;
import com.xaguzman.ai.behaviortrees.composites.Selector;
import com.xaguzman.ai.behaviortrees.composites.Sequence;
import com.xaguzman.ai.behaviortrees.decorators.DelayDecorator;

public final class BehaviorTreeBuilder {

    private BehaviorNode currentNode;
    private Array<CompositeBehaviorNode> parentNodeStack = new Array<CompositeBehaviorNode>();

    public BehaviorTreeBuilder action(String name, BehaviorAction action) throws Exception {
        if (parentNodeStack.size == 0)
        {
            throw new Exception("Can't create an un-nested ActionNode, it must be a leaf node.");
        }

        action.setName(name);
        parentNodeStack.peek().add(action);
        return this;
    }

    public BehaviorTreeBuilder delayed(String name, BehaviorNode target, float duration) throws Exception {

        if (parentNodeStack.size == 0)
        {
            throw new Exception("Can't create an un-nested ActionNode, it must be a leaf node.");
        }

        DelayDecorator delay = new DelayDecorator(target, duration);
        delay.setName(name);
        parentNodeStack.peek().add(delay);
        return this;
    }

    public BehaviorTreeBuilder delay(String name, float duration) throws Exception {
        return action(name, new DelayAction(duration));
    }

    public BehaviorTreeBuilder condition(String name, ConditionalBehaviorAction condition) throws Exception {
        return  action(name, condition);
    }

    public BehaviorTreeBuilder sequence(String name){
        Sequence newSequence = new Sequence();
        if (parentNodeStack.size > 0){
            parentNodeStack.peek().add(newSequence);
        }

        parentNodeStack.add(newSequence);

        return this;
    }

    public BehaviorTreeBuilder selector(String name){
        Selector newSelector = new Selector();
        if (parentNodeStack.size > 0){
            parentNodeStack.peek().add(newSelector);
        }

        parentNodeStack.add(newSelector);
        return this;
    }

    public BehaviorTreeBuilder parallel(String name, int requiredFailures, int requiredSuccess){
        Parallel newParallel = new Parallel();
        newParallel.requiredFailures = (byte) requiredFailures;
        newParallel.requiredSuccess = (byte) requiredSuccess;
        if (parentNodeStack.size > 0){
            parentNodeStack.peek().add(newParallel);
        }

        parentNodeStack.add(newParallel);
        return this;
    }

    public BehaviorNode build() throws Exception {
        if (currentNode == null){
            throw new Exception("Can't create behavior tree with zero nodes");
        }
        return currentNode;
    }

    public BehaviorTreeBuilder end(){
        currentNode = parentNodeStack.pop();
        return this;
    }
}



