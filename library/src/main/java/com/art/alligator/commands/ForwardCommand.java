package com.art.alligator.commands;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.art.alligator.AnimationData;
import com.art.alligator.Command;
import com.art.alligator.DialogAnimation;
import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionType;
import com.art.alligator.exceptions.CommandExecutionException;
import com.art.alligator.exceptions.FailedResolveActivityException;
import com.art.alligator.internal.ActivityHelper;
import com.art.alligator.internal.DialogFragmentHelper;
import com.art.alligator.internal.FragmentStack;
import com.art.alligator.internal.ScreenClassUtils;

/**
 * Date: 29.12.2016
 * Time: 10:22
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code goForward} method of {@link com.art.alligator.AndroidNavigator}.
 */
public class ForwardCommand implements Command {
	private Screen mScreen;
	private AnimationData mAnimationData;

	public ForwardCommand(Screen screen, AnimationData animationData) {
		mScreen = screen;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		switch (navigationFactory.getViewType(mScreen.getClass())) {
			case ACTIVITY: {
				Activity activity = navigationContext.getActivity();
				Intent intent = navigationFactory.createActivityIntent(activity, mScreen);
				ScreenClassUtils.putScreenClass(intent, mScreen.getClass());
				ScreenClassUtils.putPreviousScreenClass(intent, ScreenClassUtils.getScreenClass(activity, navigationFactory));

				ActivityHelper activityHelper = ActivityHelper.from(navigationContext);
				if (!activityHelper.resolve(intent)) {
					throw new FailedResolveActivityException(this, mScreen);
				}

				TransitionAnimation animation = getActivityAnimation(navigationContext, navigationFactory);
				if (navigationFactory.isScreenForResult(mScreen.getClass())) {
					int requestCode = navigationFactory.getRequestCode(mScreen.getClass());
					activityHelper.startForResult(intent, requestCode, animation);
				} else {
					activityHelper.start(intent, animation);
				}
				return false;
			}

			case FRAGMENT: {
				if (!navigationContext.hasContainerId()) {
					throw new CommandExecutionException(this, "ContainerId is not set.");
				}

				Fragment fragment = navigationFactory.createFragment(mScreen);
				if(fragment instanceof DialogFragment) {
					throw new CommandExecutionException(this, "DialogFragment is used as usual Fragment.");
				}

				ScreenClassUtils.putScreenClass(fragment, mScreen.getClass());
				FragmentStack fragmentStack = FragmentStack.from(navigationContext);
				TransitionAnimation animation = getFragmentAnimation(navigationContext, fragmentStack.getCurrentFragment());
				fragmentStack.push(fragment, animation);
				return true;
			}

			case DIALOG_FRAGMENT: {
				DialogFragment dialogFragment = navigationFactory.createDialogFragment(mScreen);
				DialogAnimation animation = getDialogAnimation(navigationContext);
				DialogFragmentHelper.from(navigationContext).showDialog(dialogFragment, animation);
				return true;
			}

			default:
				throw new CommandExecutionException(this, "Screen " + mScreen.getClass().getSimpleName() + " is not registered.");
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(navigationContext.getActivity(), navigationFactory);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		return navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.FORWARD, screenClassFrom, screenClassTo, true, mAnimationData);
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext, Fragment currentFragment) {
		if (currentFragment == null) {
			return TransitionAnimation.DEFAULT;
		}

		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		return navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.FORWARD, screenClassFrom, screenClassTo, false, mAnimationData);
	}

	private DialogAnimation getDialogAnimation(NavigationContext navigationContext) {
		return navigationContext.getDialogAnimationProvider().getAnimation(mScreen.getClass(), mAnimationData);
	}
}
