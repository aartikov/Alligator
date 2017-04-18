package me.aartikov.alligator.animations.transition;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import me.aartikov.alligator.TransitionAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:01
 *
 * @author Artur Artikov
 */

/**
 * Transition animation that does nothing.
 */
public class DummyTransitionAnimation implements TransitionAnimation {
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
	}

	@Override
	public void applyAfterActivityFinished(Activity activity) {
	}

	@Override
	public void applyBeforeFragmentTransactionExecuted(FragmentTransaction transaction, Fragment enteringFragment, Fragment exitingFragment) {
	}

	@Override
	public void applyAfterFragmentTransactionExecuted(Fragment enteringFragment, Fragment exitingFragment) {
	}
}
