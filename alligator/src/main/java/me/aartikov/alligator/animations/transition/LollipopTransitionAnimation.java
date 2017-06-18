package me.aartikov.alligator.animations.transition;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.transition.Transition;
import android.view.View;

import me.aartikov.alligator.TransitionAnimation;

/**
 * Date: 16.04.2017
 * Time: 13:59
 *
 * @author Artur Artikov
 */

/**
 * Transition animation that uses activity and fragment transitions introduced in Lollipop (API 21).
 * Warning: some methods are not supported for activities (see method description).
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LollipopTransitionAnimation implements TransitionAnimation {
	private Transition mEnterTransition;
	private Transition mExitTransition;
	private Transition mSharedElementTransition;
	private boolean mAllowEnterTransitionOverlap = true;
	private List<Pair<View, String>> mSharedElements;

	/**
	 * Creates with default settings. Can be used for activities to enable Lollipop transitions (Window.FEATURE_ACTIVITY_TRANSITIONS should be also set for an activity).
	 */
	public LollipopTransitionAnimation() {
	}

	/**
	 * Creates with given enter and exit transitions. Warning: is not supported for activities.
	 *
	 * @param enterTransition transition that will be used for an appearing fragment
	 * @param exitTransition  transition that will be used for a disappearing fragment
	 */
	public LollipopTransitionAnimation(Transition enterTransition, Transition exitTransition) {
		mEnterTransition = enterTransition;
		mExitTransition = exitTransition;
	}

	public Transition getEnterTransition() {
		return mEnterTransition;
	}

	/**
	 * Sets a transition that will be used for an appearing fragment. Warning: is not supported for activities, should be set manually in activity's onCreate method.
	 *
	 * @param enterTransition transition that will be used for an appearing fragment
	 * @return this object
	 */
	public LollipopTransitionAnimation setEnterTransition(Transition enterTransition) {
		mEnterTransition = enterTransition;
		return this;
	}

	public Transition getExitTransition() {
		return mExitTransition;
	}

	/**
	 * Sets a transition that will be used for a disappearing fragment. Warning: is not supported for activities, should be set manually in activity's onCreate method.
	 *
	 * @param exitTransition transition that will be used for a disappearing fragment
	 * @return this object
	 */
	public LollipopTransitionAnimation setExitTransition(Transition exitTransition) {
		mExitTransition = exitTransition;
		return this;
	}

	public Transition getSharedElementTransition() {
		return mSharedElementTransition;
	}

	/**
	 * Sets a transition that will be used for shared elements. Warning: is not supported for activities, should be set manually in activity's onCreate method.
	 *
	 * @param sharedElementTransition transition that will be used for shared elements
	 * @return this object
	 */
	public LollipopTransitionAnimation setSharedElementTransition(Transition sharedElementTransition) {
		mSharedElementTransition = sharedElementTransition;
		return this;
	}

	public boolean isAllowEnterTransitionOverlap() {
		return mAllowEnterTransitionOverlap;
	}

	/**
	 * Sets whether the exit transition and the enter transition overlap or not. Warning: is not supported for activities, should be set manually in activity's onCreate method.
	 *
	 * @param allowEnterTransitionOverlap true to start the enter transition when possible or false to wait until the exiting transition completes
	 * @return this object
	 */
	public LollipopTransitionAnimation setAllowEnterTransitionOverlap(boolean allowEnterTransitionOverlap) {
		mAllowEnterTransitionOverlap = allowEnterTransitionOverlap;
		return this;
	}

	public List<Pair<View, String>> getSharedElements() {
		return mSharedElements;
	}

	/**
	 * Adds a shared element that will be used in a transition.
	 *
	 * @param sharedElement shared element view in a disappearing activity/fragment
	 * @param name          shared element name in an appearing activity/fragment
	 * @return this object
	 */
	public LollipopTransitionAnimation addSharedElement(View sharedElement, String name) {
		if (mSharedElements == null) {
			mSharedElements = new ArrayList<>();
		}
		mSharedElements.add(new Pair<>(sharedElement, name));
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Bundle getActivityOptionsBundle(Activity activity) {
		if (mSharedElements == null) {
			return ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle();
		} else {
			Pair<View, String>[] sharedElements = mSharedElements.toArray(new Pair[mSharedElements.size()]);
			return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElements).toBundle();
		}
	}

	@Override
	public boolean needDelayActivityFinish() {
		return true;
	}

	@Override
	public void applyBeforeActivityStarted(Activity currentActivity, Intent intent) {
	}

	@Override
	public void applyAfterActivityStarted(Activity currentActivity) {
	}

	@Override
	public void applyBeforeActivityFinished(Activity activity) {
	}

	@Override
	public void applyAfterActivityFinished(Activity activity) {
	}

	@Override
	public void applyBeforeFragmentTransactionExecuted(FragmentTransaction transaction, Fragment enteringFragment, Fragment exitingFragment) {
		enteringFragment.setEnterTransition(mEnterTransition);
		exitingFragment.setExitTransition(mExitTransition);
		enteringFragment.setSharedElementEnterTransition(mSharedElementTransition);
		enteringFragment.setAllowEnterTransitionOverlap(mAllowEnterTransitionOverlap);
		if (mSharedElements != null) {
			for (Pair<View, String> sharedElement : mSharedElements) {
				transaction.addSharedElement(sharedElement.first, sharedElement.second);
			}
		}
	}

	@Override
	public void applyAfterFragmentTransactionExecuted(Fragment enteringFragment, Fragment exitingFragment) {
		enteringFragment.setEnterTransition(null);
		exitingFragment.setExitTransition(null);
		enteringFragment.setSharedElementEnterTransition(null);
		enteringFragment.setAllowEnterTransitionOverlap(true);
	}
}
