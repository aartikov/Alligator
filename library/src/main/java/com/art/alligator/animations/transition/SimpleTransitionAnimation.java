package com.art.alligator.animations.transition;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.v4.app.FragmentTransaction;

import com.art.alligator.TransitionAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:02
 *
 * @author Artur Artikov
 */

/**
 * Transition animation that uses anim resources.
 */
public class SimpleTransitionAnimation implements TransitionAnimation {
	private int mEnterAnimation;
	private int mExitAnimation;

	public SimpleTransitionAnimation(@AnimRes int enterAnimation, @AnimRes int exitAnimation) {
		mEnterAnimation = enterAnimation;
		mExitAnimation = exitAnimation;
	}

	@Override
	public Bundle getActivityOptionsBundle(Activity activity) {
		return null;
	}

	@Override
	public boolean needDelayActivityFinish() {
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
