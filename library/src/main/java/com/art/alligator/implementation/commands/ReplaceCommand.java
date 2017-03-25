package com.art.alligator.implementation.commands;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.art.alligator.AnimationData;
import com.art.alligator.Command;
import com.art.alligator.CommandExecutionException;
import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionType;
import com.art.alligator.implementation.CommandUtils;
import com.art.alligator.implementation.FailedResolveActivityException;
import com.art.alligator.implementation.FragmentStack;
import com.art.alligator.implementation.ScreenClassUtils;

/**
 * Date: 29.12.2016
 * Time: 11:24
 *
 * @author Artur Artikov
 */
public class ReplaceCommand implements Command {
	private Screen mScreen;
	private AnimationData mAnimationData;

	public ReplaceCommand(Screen screen, AnimationData animationData) {
		mScreen = screen;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		if (navigationFactory.isActivityScreen(mScreen.getClass())) {
			Activity activity = navigationContext.getActivity();
			Intent intent = navigationFactory.createIntent(activity, mScreen);
			ScreenClassUtils.putScreenClass(intent, mScreen.getClass());
			ScreenClassUtils.putPreviousScreenClass(intent, ScreenClassUtils.getPreviousScreenClass(activity));

			if (intent.resolveActivity(activity.getPackageManager()) == null) {
				throw new FailedResolveActivityException(this, mScreen);
			}
			activity.startActivity(intent);

			activity.finish();
			CommandUtils.applyActivityAnimation(activity, getActivityAnimation(navigationContext, navigationFactory));
			return false;

		} else if (navigationFactory.isFragmentScreen(mScreen.getClass())) {
			if (navigationContext.getContainerId() <= 0) {
				throw new CommandExecutionException(this, "ContainerId is not set.");
			}

			Fragment fragment = navigationFactory.createFragment(mScreen);
			ScreenClassUtils.putScreenClass(fragment, mScreen.getClass());
			FragmentStack fragmentStack = FragmentStack.from(navigationContext);
			TransitionAnimation animation = getFragmentAnimation(navigationContext, fragmentStack.getCurrentFragment());
			fragmentStack.replace(fragment, animation);
			return true;
		} else {
			throw new CommandExecutionException(this, "Screen " + mScreen.getClass().getSimpleName() + " is not registered.");
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(navigationContext.getActivity(), navigationFactory);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		return navigationContext.getAnimationProvider().getAnimation(TransitionType.REPLACE, screenClassFrom, screenClassTo, true, mAnimationData);
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext, Fragment currentFragment) {
		if (currentFragment == null) {
			return TransitionAnimation.NONE;
		}

		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		return navigationContext.getAnimationProvider().getAnimation(TransitionType.REPLACE, screenClassFrom, screenClassTo, false, mAnimationData);
	}
}