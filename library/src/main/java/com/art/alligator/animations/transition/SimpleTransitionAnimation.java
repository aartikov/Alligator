package com.art.alligator.animations.transition;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.art.alligator.TransitionAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:02
 *
 * @author Artur Artikov
 */

public class SimpleTransitionAnimation implements TransitionAnimation {
	private int mEnterAnimation;
	private int mExitAnimation;

	public SimpleTransitionAnimation(int enterAnimation, int exitAnimation) {
		mEnterAnimation = enterAnimation;
		mExitAnimation = exitAnimation;
	}

	@Override
	public Bundle getActivityOptionsBundle(Activity activity) {
		return null;
	}

	@Override
	public boolean needDelayActivityFinish(Activity activity) {
		return false;
	}

	@Override
	public void applyToActivityAfterStart(Activity activity) {
		activity.overridePendingTransition(mEnterAnimation, mExitAnimation);
	}

	@Override
	public void applyToActivityAfterFinish(Activity activity) {
		activity.overridePendingTransition(mEnterAnimation, mExitAnimation);
	}

	@Override
	public void applyToFragmentTransaction(FragmentTransaction transaction) {
		transaction.setCustomAnimations(mEnterAnimation, mExitAnimation);
	}
}
