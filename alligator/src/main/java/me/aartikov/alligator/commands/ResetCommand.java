package me.aartikov.alligator.commands;

import android.content.Intent;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.Command;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionAnimation;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.exceptions.CommandExecutionException;
import me.aartikov.alligator.exceptions.FailedResolveActivityException;
import me.aartikov.alligator.internal.ActivityHelper;
import me.aartikov.alligator.internal.FragmentStack;
import me.aartikov.alligator.internal.ScreenClassUtils;

/**
 * Date: 29.12.2016
 * Time: 14:30
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code reset} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class ResetCommand implements Command {
	private Screen mScreen;
	private AnimationData mAnimationData;

	public ResetCommand(Screen screen, AnimationData animationData) {
		mScreen = screen;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		switch (navigationFactory.getViewType(mScreen.getClass())) {
			case ACTIVITY: {
				Intent intent = navigationFactory.createActivityIntent(navigationContext.getActivity(), mScreen);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				ScreenClassUtils.putScreenClass(intent, mScreen.getClass());

				ActivityHelper activityHelper = ActivityHelper.from(navigationContext);
				if (!activityHelper.resolve(intent)) {
					throw new FailedResolveActivityException(this, mScreen);
				}
				TransitionAnimation animation = getActivityAnimation(navigationContext, navigationFactory);
				activityHelper.start(intent, animation);
				return false;
			}

			case FRAGMENT: {
				if (!navigationContext.hasContainerId()) {
					throw new CommandExecutionException(this, "ContainerId is not set.");
				}

				Fragment fragment = navigationFactory.createFragment(mScreen);
				ScreenClassUtils.putScreenClass(fragment, mScreen.getClass());
				FragmentStack fragmentStack = FragmentStack.from(navigationContext);
				TransitionAnimation animation = getFragmentAnimation(navigationContext, fragmentStack.getCurrentFragment());
				fragmentStack.reset(fragment, animation);
				return true;
			}

			case DIALOG_FRAGMENT:
				throw new CommandExecutionException(this, "This command is not supported for dialog fragment screen.");

			default:
				throw new CommandExecutionException(this, "Screen " + mScreen.getClass().getSimpleName() + " is not registered.");
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(navigationContext.getActivity(), navigationFactory);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		return navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.RESET, screenClassFrom, screenClassTo, true, mAnimationData);
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext, Fragment currentFragment) {
		if (currentFragment == null) {
			return TransitionAnimation.DEFAULT;
		}

		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		return navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.RESET, screenClassFrom, screenClassTo, false, mAnimationData);
	}
}
