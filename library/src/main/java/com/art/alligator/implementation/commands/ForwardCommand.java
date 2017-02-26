package com.art.alligator.implementation.commands;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.art.alligator.Command;
import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionAnimationDirection;
import com.art.alligator.implementation.CommandUtils;
import com.art.alligator.implementation.ScreenUtils;

/**
 * Date: 29.12.2016
 * Time: 10:22
 *
 * @author Artur Artikov
 */
public class ForwardCommand implements Command {
	private Screen mScreen;

	public ForwardCommand(Screen screen) {
		mScreen = screen;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Intent intent = navigationFactory.createActivityIntent(navigationContext.getActivity(), mScreen);
		Fragment fragment = navigationFactory.createFragment(mScreen);

		if (intent != null) {
			Activity activity = navigationContext.getActivity();
			ScreenUtils.putScreenClass(intent, mScreen.getClass());
			activity.startActivity(intent);
			CommandUtils.applyActivityAnimation(activity, getActivityAnimation(navigationContext));
			return false;

		} else if (fragment != null) {
			FragmentManager fragmentManager = navigationContext.getFragmentManager();
			if (fragmentManager == null) {
				throw new IllegalStateException("Failed to add fragment. FragmentManager is not bound.");
			}

			FragmentTransaction transaction = fragmentManager.beginTransaction();
			Fragment currentFragment = CommandUtils.getCurrentFragment(navigationContext);
			if(currentFragment != null) {
				CommandUtils.applyFragmentAnimation(transaction, getFragmentAnimation(navigationContext));
				transaction.detach(currentFragment);
			}

			ScreenUtils.putScreenClass(fragment, mScreen.getClass());
			int index = CommandUtils.getFragmentCount(navigationContext);
			String tag = CommandUtils.getFragmentTag(navigationContext, index);
			transaction.add(navigationContext.getContainerId(), fragment, tag);
			transaction.commitNow();
			return true;

		} else {
			throw new RuntimeException("Screen " + mScreen.getClass().getSimpleName() + " is not registered.");
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext) {
		return navigationContext.getAnimationProvider().getAnimation(TransitionAnimationDirection.FORWARD, true, mScreen.getClass());
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext) {
		return navigationContext.getAnimationProvider().getAnimation(TransitionAnimationDirection.FORWARD, false, mScreen.getClass());
	}
}
