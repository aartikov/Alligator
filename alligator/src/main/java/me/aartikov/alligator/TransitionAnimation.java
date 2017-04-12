package me.aartikov.alligator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import me.aartikov.alligator.animations.transition.DummyTransitionAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:01
 *
 * @author Artur Artikov
 */

/**
 * Animation that played during transition from one screen to another.
 */
public interface TransitionAnimation {
	TransitionAnimation DEFAULT = new DummyTransitionAnimation();

	/**
	 * Called before starting of an activity. Used to pass an options bundle to a started activity.
	 *
	 * @param activity current activity
	 * @return options bundle for activity starting. Can be {@code null} if there are no options needed.
	 */
	Bundle getActivityOptionsBundle(Activity activity);

	/**
	 * Called before finishing of an activity. Checks if there is need to delay an activity finish.
	 * <p>
	 * An activity will finish using {@code supportFinishAfterTransition} if this method returns {@code true}, otherwise - using {@code finish}.
	 *
	 * @return true if an activity finish should be delayed
	 */
	boolean needDelayActivityFinish();

	/**
	 * Called after starting of an activity.
	 *
	 * @param activity current activity
	 */
	void applyToActivityAfterStart(Activity activity);

	/**
	 * Called after finishing of an activity.
	 *
	 * @param activity finished activity
	 */
	void applyToActivityAfterFinish(Activity activity);

	/**
	 * Called after beginning of a fragment transition.
	 *
	 * @param transaction fragment transaction
	 */
	void applyToFragmentTransaction(FragmentTransaction transaction);
}
