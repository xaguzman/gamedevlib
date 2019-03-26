package com.xaguzman.ai.behaviortrees.actions;

import com.xaguzman.ai.behaviortrees.BehaviorNodeResult;

/**
 * A node that will only ever return either {@link BehaviorNodeResult#Success} or {@link BehaviorNodeResult#Failure} given a condition
 * @author Xavier
 *
 */
public abstract class ConditionalBehaviorAction extends BehaviorAction {

	@Override
	public BehaviorNodeResult act(float delta) {
		BehaviorNodeResult result = conditionMet() ? BehaviorNodeResult.Success : BehaviorNodeResult.Failure;
		
		return result;
	}
	
	@Override
	public void restart() {
		
	}

	public abstract boolean conditionMet();
}
