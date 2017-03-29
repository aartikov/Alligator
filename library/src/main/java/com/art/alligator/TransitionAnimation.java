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

/**
 *  Animation that played during transition from one screen to another.
 */
public interface TransitionAnimation {
	TransitionAnimation DEFAULT = new DummyTransitionAnimation();

	/**
	 * Returns options bundle than passes to startActivity method. Can be null.
	 */
	Bundle getActivityOptionsBundle(Activity activity);

	/**
	 * Check if need delay activity finish. If returns true - activity finishes with method supportFinishAfterTransition(), otherwise - with method finish()
	 */
	boolean needDelayActivityFinish();

	/**
	 * Called after startActivity method
	 */
	void applyToActivityAfterStart(Activity activity);

	/**
	 * Called after activity finished
	 */
	void applyToActivityAfterFinish(Activity activity);

	/**
	 * Called after fragment transition is begun
	 */
	void applyToFragmentTransaction(FragmentTransaction transaction);
}
