package com.art.alligator.implementation;

import android.app.Activity;

import com.art.alligator.TransitionAnimation;
import com.art.alligator.AnimationProvider;
import com.art.alligator.Screen;

/**
 * Date: 24.02.2017
 * Time: 19:14
 *
 * @author Artur Artikov
 */
public class DefaultAnimationProvider implements AnimationProvider {
	@Override
	public TransitionAnimation getActivityForwardAnimation(Class<? extends Screen> screenClass) {
		return TransitionAnimation.DEFAULT;
	}

	@Override
	public TransitionAnimation getActivityBackAnimation(Class<? extends Activity> activityClass) {
		return TransitionAnimation.DEFAULT;
	}

	@Override
	public TransitionAnimation getActivityReplaceAnimation(Class<? extends Screen> screenClass) {
		return TransitionAnimation.DEFAULT;
	}

	@Override
	public TransitionAnimation getFragmentForwardAnimation(Class<? extends Screen> screenClass) {
		return TransitionAnimation.NONE;
	}

	@Override
	public TransitionAnimation getFragmentBackAnimation(Class<? extends Screen> screenClass) {
		return TransitionAnimation.NONE;
	}

	@Override
	public TransitionAnimation getFragmentReplaceAnimation(Class<? extends Screen> screenClass) {
		return TransitionAnimation.NONE;
	}
}
