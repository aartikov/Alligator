package com.art.alligator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.art.alligator.animations.transition.DummyTransitionAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:01
 *
 * @author Artur Artikov
 */

public interface TransitionAnimation {
	TransitionAnimation DEFAULT = new DummyTransitionAnimation();

	Bundle getActivityOptionsBundle(Activity activity);

	boolean needDelayActivityFinish();

	void applyToActivityAfterStart(Activity activity);

	void applyToActivityAfterFinish(Activity activity);

	void applyToFragmentTransaction(FragmentTransaction transaction);
}
