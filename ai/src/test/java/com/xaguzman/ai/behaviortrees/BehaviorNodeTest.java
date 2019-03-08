package com.xaguzman.ai.behaviortrees;

import com.xaguzman.ai.behaviortrees.actions.DelegateAction;
import static org.junit.Assert.*;
import org.junit.Test;

public class BehaviorNodeTest {

    @Test
    public void canRunDelegateAction(){
        final float delta = 1f / 30f;
        final int[] invokeCount = {0};

        BehaviorNodeResult testResult = new DelegateAction(new BehaviorNode(){

            @Override
            public BehaviorNodeResult act(float t) {
                assertEquals("Delta time passed correctly", t, delta, 0);
                ++invokeCount[0];
                return BehaviorNodeResult.Running;
            }

            @Override
            public void restart() {

            }
        }).act(delta);

        assertEquals(BehaviorNodeResult.Running, testResult);
        assertEquals(1, invokeCount[0]);
    }
}
