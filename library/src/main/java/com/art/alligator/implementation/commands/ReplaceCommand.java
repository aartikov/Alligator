package com.art.alligator.implementation.commands;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.art.alligator.TransitionAnimation;
import com.art.alligator.AnimationProvider;
import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.implementation.Command;

/**
 * Date: 29.12.2016
 * Time: 11:24
 *
 * @author Artur Artikov
 */
public class ReplaceCommand implements Command {
	private Screen mScreen;

	public ReplaceCommand(Screen screen) {
		mScreen = screen;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Activity activity = navigationContext.getActivity();
		Intent intent = navigationFactory.createActivityIntent(activity, mScreen);
		Fragment fragment = navigationFactory.createFragment(mScreen);
		AnimationProvider animationProvider = navigationContext.getAnimationProvider();

		if(intent != null) {
			activity.startActivity(intent);
			TransitionAnimation animation = animationProvider.getActivityReplaceAnimation(mScreen.getClass());
			if(animation != null && !animation.equals(TransitionAnimation.DEFAULT)) {
				activity.overridePendingTransition(animation.getEnterAnimation(), animation.getExitAnimation());
			}
			activity.finish();
			return false;
		} else if (fragment != null) {
			FragmentManager fragmentManager = navigationContext.getFragmentManager();
			if (fragmentManager == null) {
				throw new IllegalStateException("Failed to replace fragment. FragmentManager is not bound.");
			}

			if (fragmentManager.getBackStackEntryCount() > 0) {
				fragmentManager.popBackStackImmediate();
			}

			TransitionAnimation replaceAnimation = animationProvider.getFragmentReplaceAnimation(mScreen.getClass());
			if(replaceAnimation == null || replaceAnimation.equals(TransitionAnimation.DEFAULT)) {
				replaceAnimation = TransitionAnimation.NONE;
			}

			TransitionAnimation backAnimation = animationProvider.getFragmentBackAnimation(mScreen.getClass());
			if(backAnimation == null || backAnimation.equals(TransitionAnimation.DEFAULT)) {
				backAnimation = TransitionAnimation.NONE;
			}

			fragmentManager.beginTransaction()
					.setCustomAnimations(replaceAnimation.getEnterAnimation(), replaceAnimation.getExitAnimation(), backAnimation.getEnterAnimation(), backAnimation.getExitAnimation())
					.replace(navigationContext.getContainerId(), fragment)
					.addToBackStack(mScreen.getClass().getName())
					.commit();
			fragmentManager.executePendingTransactions();
			return true;

		} else {
			throw new RuntimeException("Screen " + mScreen.getClass().getSimpleName() + " is not registered.");
		}
	}
}