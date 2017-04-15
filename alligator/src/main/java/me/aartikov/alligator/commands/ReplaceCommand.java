package me.aartikov.alligator.commands;

import android.app.Activity;
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
 * Time: 11:24
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code replace} method of {@link me.aartikov.alligator.AndroidNavigator}.
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
		switch (navigationFactory.getViewType(mScreen.getClass())) {
			case ACTIVITY: {
				Activity activity = navigationContext.getActivity();
				Intent intent = navigationFactory.createActivityIntent(activity, mScreen);
				ScreenClassUtils.putScreenClass(intent, mScreen.getClass());
				ScreenClassUtils.putPreviousScreenClass(intent, ScreenClassUtils.getPreviousScreenClass(activity));

				ActivityHelper activityHelper = ActivityHelper.from(navigationContext);
				if (!activityHelper.resolve(intent)) {
					throw new FailedResolveActivityException(this, mScreen);
				}

				Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(activity, navigationFactory);
				Class<? extends Screen> screenClassTo = mScreen.getClass();
				TransitionAnimation animation = TransitionAnimation.DEFAULT;
				if (screenClassFrom != null) {
					animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.REPLACE, screenClassFrom, screenClassTo, true, mAnimationData);
				}

				activityHelper.start(intent, animation);
				activityHelper.finish(animation);
				navigationContext.getNavigationListener().onScreenTransition(TransitionType.REPLACE, screenClassFrom, screenClassTo, true);
				return false;
			}

			case FRAGMENT: {
				if (!navigationContext.hasContainerId()) {
					throw new CommandExecutionException(this, "ContainerId is not set.");
				}

				Fragment fragment = navigationFactory.createFragment(mScreen);
				ScreenClassUtils.putScreenClass(fragment, mScreen.getClass());
				FragmentStack fragmentStack = FragmentStack.from(navigationContext);
				Fragment currentFragment = fragmentStack.getCurrentFragment();

				Class<? extends Screen> screenClassFrom = currentFragment == null ? null : ScreenClassUtils.getScreenClass(currentFragment);
				Class<? extends Screen> screenClassTo = mScreen.getClass();
				TransitionAnimation animation = TransitionAnimation.DEFAULT;
				if (screenClassFrom != null) {
					animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.REPLACE, screenClassFrom, screenClassTo, false, mAnimationData);
				}

				fragmentStack.replace(fragment, animation);
				navigationContext.getNavigationListener().onScreenTransition(TransitionType.REPLACE, screenClassFrom, screenClassTo, false);
				return true;
			}

			case DIALOG_FRAGMENT:
				throw new CommandExecutionException(this, "This command is not supported for dialog fragment screen.");

			default:
				throw new CommandExecutionException(this, "Screen " + mScreen.getClass().getSimpleName() + " is not unknown.");
		}
	}
}