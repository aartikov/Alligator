package me.aartikov.alligator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import me.aartikov.alligator.animations.transition.DummyTransitionAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:01
 *
 * @author Artur Artikov
 */

/**
 * Animation that played during a transition from one screen to another.
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
	 * Called before starting of an activity.
	 *
	 * @param currentActivity activity that will start another activity
	 * @param intent intent that will be used to start an activity
	 */
	void applyBeforeActivityStarted(Activity currentActivity, Intent intent);

	/**
	 * Called after starting of an activity.
	 *
	 * @param currentActivity activity that started another activity
	 */
	void applyAfterActivityStarted(Activity currentActivity);

	/**
	 * Called before finishing of an activity.
	 *
	 * @param activity that will finish
	 */
	void applyBeforeActivityFinished(Activity activity);

	/**
	 * Called after finishing of an activity.
	 *
	 * @param activity finished activity
	 */
	void applyAfterActivityFinished(Activity activity);

	/**
	 * Called before a fragment transaction executed.
	 *
	 * @param transaction      fragment transaction
	 * @param enteringFragment fragment that will be added/attached during the transaction
	 * @param exitingFragment  fragment that will be removed/detached  during the transaction
	 */
	void applyBeforeFragmentTransactionExecuted(FragmentTransaction transaction, Fragment enteringFragment, Fragment exitingFragment);

	/**
	 * Called after a fragment transaction executed.
	 *
	 * @param enteringFragment fragment that will be added/attached during the transaction
	 * @param exitingFragment  fragment that will be removed/detached  during the transaction
	 */
	void applyAfterFragmentTransactionExecuted(Fragment enteringFragment, Fragment exitingFragment);
}
