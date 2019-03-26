package com.xaguzman.ai.behaviortrees.decorators;


import com.xaguzman.ai.behaviortrees.BehaviorNode;
import com.xaguzman.ai.behaviortrees.BehaviorNodeResult;

/**
 * Decorator to force the restart of the given  {@link BehaviorNode} after the resetInterval has passed.
 * @author Xavier Guzman
 *
 */
public class TimedResetDecorator extends BehaviorNodeDecorator {

	float resetInterval;
	float timetillNextReset;
	
	public TimedResetDecorator(BehaviorNode target, float resetInterval) {
		super(target);
		this.resetInterval = resetInterval;
		this.timetillNextReset = this.resetInterval;
	}

	@Override
	public BehaviorNodeResult act(float delta) {
		timetillNextReset -= delta;
		if (timetillNextReset > 0)
			return target.act(delta);
		
		restart();
		return BehaviorNodeResult.Success;
	}

	@Override
	public void restart() {
		target.restart();
		timetillNextReset = resetInterval;
	}

}
