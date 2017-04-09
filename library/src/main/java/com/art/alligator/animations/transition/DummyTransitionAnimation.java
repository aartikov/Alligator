package com.art.alligator.animations.transition;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.art.alligator.TransitionAnimation;

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
	public void applyToActivityAfterStart(Activity activity) {
	}

	@Override
	public void applyToActivityAfterFinish(Activity activity) {
	}

	@Override
	public void applyToFragmentTransaction(FragmentTransaction transaction) {
	}
}
