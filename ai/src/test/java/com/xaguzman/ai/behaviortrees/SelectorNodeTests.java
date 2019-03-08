package com.xaguzman.ai.behaviortrees;

import com.xaguzman.ai.behaviortrees.composites.Selector;
import junit.framework.Assert;
import org.junit.Test;

public final class SelectorNodeTests {

    private Selector selectorNode = new Selector();


    public final Selector getSelectorNode() {
        return this.selectorNode;
    }

    public final void setSelectorNode(Selector var1) {
        this.selectorNode = var1;
    }

    public final void init() {
        this.selectorNode.getChildren().clear();
    }

    @Test
    public final void stopsOnFirstSuccessOrRunning() {
        this.init();
        BehaviorNode mockChild1 = new BehaviorNode(){
            @Override
            public BehaviorNodeResult act(float delta) {
                return BehaviorNodeResult.Failure;
            }
            @Override
            public void restart() { }

        };
        BehaviorNode mockChild2 = new BehaviorNode(){
            @Override
            public BehaviorNodeResult act(float delta) {
                return BehaviorNodeResult.Success;
            }
            @Override
            public void restart() { }

        };
        BehaviorNode mockChild3 = new BehaviorNode(){
            @Override
            public BehaviorNodeResult act(float delta) {
                return BehaviorNodeResult.Running;
            }
            @Override
            public void restart() { }

        };
        this.selectorNode.add(mockChild1);
        this.selectorNode.add(mockChild2);
        this.selectorNode.add(mockChild3);
        Assert.assertEquals(BehaviorNodeResult.Success, this.selectorNode.act(0.3F));

        this.init();
        this.selectorNode.add(mockChild1);
        this.selectorNode.add(mockChild3);
        this.selectorNode.add(mockChild2);
        Assert.assertEquals(BehaviorNodeResult.Running, this.selectorNode.act(0.3F));
    }

    @Test
    public final void failsOnAllChildrenFail() {
        this.init();
        BehaviorNode mockChild1 = new BehaviorNode(){
            @Override
            public BehaviorNodeResult act(float delta) {
                return BehaviorNodeResult.Failure;
            }
            @Override
            public void restart() { }

        };
        BehaviorNode mockChild2 = new BehaviorNode(){
            @Override
            public BehaviorNodeResult act(float delta) {
                return BehaviorNodeResult.Failure;
            }
            @Override
            public void restart() { }

        };
        BehaviorNode mockChild3 = new BehaviorNode(){
            @Override
            public BehaviorNodeResult act(float delta) {
                return BehaviorNodeResult.Failure;
            }
            @Override
            public void restart() { }

        };
        this.selectorNode.add(mockChild1);
        this.selectorNode.add(mockChild3);
        this.selectorNode.add(mockChild2);
        Assert.assertEquals(BehaviorNodeResult.Failure, this.selectorNode.act(0.3F));
    }
}
