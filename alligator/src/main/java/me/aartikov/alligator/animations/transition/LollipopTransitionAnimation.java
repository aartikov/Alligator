package me.aartikov.alligator.animations.transition;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LollipopTransitionAnimation implements TransitionAnimation {
	private Transition mEnterTransition;
	private Transition mExitTransition;
	private Transition mSharedElementTransition;
	private boolean mAllowEnterTransitionOverlap = true;
	private List<Pair<View, String>> mSharedElements;

	public LollipopTransitionAnimation() {
	}

	public LollipopTransitionAnimation(Transition enterTransition, Transition exitTransition) {
		mEnterTransition = enterTransition;
		mExitTransition = exitTransition;
	}

	public Transition getEnterTransition() {
		return mEnterTransition;
	}

	public LollipopTransitionAnimation setEnterTransition(Transition enterTransition) {
		mEnterTransition = enterTransition;
		return this;
	}

	public Transition getExitTransition() {
		return mExitTransition;
	}

	public LollipopTransitionAnimation setExitTransition(Transition exitTransition) {
		mExitTransition = exitTransition;
		return this;
	}

	public Transition getSharedElementTransition() {
		return mSharedElementTransition;
	}

	public LollipopTransitionAnimation setSharedElementTransition(Transition sharedElementTransition) {
		mSharedElementTransition = sharedElementTransition;
		return this;
	}

	public boolean isAllowEnterTransitionOverlap() {
		return mAllowEnterTransitionOverlap;
	}

	public LollipopTransitionAnimation setAllowEnterTransitionOverlap(boolean allowEnterTransitionOverlap) {
		mAllowEnterTransitionOverlap = allowEnterTransitionOverlap;
		return this;
	}

	public List<Pair<View, String>> getSharedElements() {
		return mSharedElements;
	}

	public LollipopTransitionAnimation addSharedElement(View sharedElement, String name) {
		if (mSharedElements == null) {
			mSharedElements = new ArrayList<>();
		}
		mSharedElements.add(new Pair<>(sharedElement, name));
		return this;
	}

	@Override
	public Bundle getActivityOptionsBundle(Activity activity) {
		return null;
	}

	@Override
	public boolean needDelayActivityFinish() {
		return true;
	}

	@Override
	public void applyAfterActivityStarted(Activity currentActivity) {
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
		if(mSharedElements != null) {
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
