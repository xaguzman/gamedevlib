package com.xaguzman.artemiscommons.components;

/**
 * Created by Xavier on 07/04/2017.
 */
public interface Tweener {
	/**
	 * Initializes the targetValues, looping, etc
	* */
	void setup();

	void update(int step, int lastStep, boolean isIterationStep, float delta);

	void forceEndValues();

	void forceStartValues();
}
