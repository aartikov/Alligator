package me.aartikov.alligator.animations.transition;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import me.aartikov.alligator.TransitionAnimation;

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
	public void applyAfterActivityStarted(Activity currentActivity) {
		currentActivity.overridePendingTransition(mEnterAnimation, mExitAnimation);
	}

	@Override
	public void applyAfterActivityFinished(Activity activity) {
		activity.overridePendingTransition(mEnterAnimation, mExitAnimation);
	}

	@Override
	public void applyBeforeFragmentTransactionExecuted(FragmentTransaction transaction, Fragment enteringFragment, Fragment exitingFragment) {
		transaction.setCustomAnimations(mEnterAnimation, mExitAnimation);
	}

	@Override
	public void applyAfterFragmentTransactionExecuted(Fragment enteringFragment, Fragment exitingFragment) {
	}
}
