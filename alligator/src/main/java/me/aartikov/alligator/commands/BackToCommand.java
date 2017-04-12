package me.aartikov.alligator.commands;

import java.util.List;

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
import me.aartikov.alligator.internal.ActivityHelper;
import me.aartikov.alligator.internal.FragmentStack;
import me.aartikov.alligator.internal.ScreenClassUtils;

/**
 * Date: 11.02.2017
 * Time: 11:14
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code goBackTo} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class BackToCommand implements Command {
	private Class<? extends Screen> mScreenClass;
	private AnimationData mAnimationData;

	public BackToCommand(Class<? extends Screen> screenClass, AnimationData animationData) {
		mScreenClass = screenClass;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		switch (navigationFactory.getViewType(mScreenClass)) {
			case ACTIVITY: {
				Class activityClass = navigationFactory.getActivityClass(mScreenClass);
				if (activityClass == null) {
					throw new CommandExecutionException(this, "Activity class for " + mScreenClass.getSimpleName() + " is null.");
				}

				Activity activity = navigationContext.getActivity();
				Intent intent = new Intent(activity, activityClass);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				ScreenClassUtils.putScreenClass(intent, mScreenClass);

				ActivityHelper activityHelper = ActivityHelper.from(navigationContext);
				TransitionAnimation animation = getActivityAnimation(navigationContext, navigationFactory);
				activityHelper.start(intent, animation);
				return false;
			}

			case FRAGMENT: {
				if (!navigationContext.hasContainerId()) {
					throw new CommandExecutionException(this, "ContainerId is not set.");
				}

				FragmentStack fragmentStack = FragmentStack.from(navigationContext);
				List<Fragment> fragments = fragmentStack.getFragments();
				Fragment fragment = null;
				for (int i = fragments.size() - 1; i >= 0; i--) {
					if (mScreenClass == ScreenClassUtils.getScreenClass(fragments.get(i))) {
						fragment = fragments.get(i);
						break;
					}
				}

				if (fragment == null) {
					throw new CommandExecutionException(this, "Screen " + mScreenClass.getSimpleName() + " is not found.");
				}

				TransitionAnimation animation = getFragmentAnimation(navigationContext, fragments.get(fragments.size() - 1));
				fragmentStack.popUntil(fragment, animation);
				return true;
			}

			case DIALOG_FRAGMENT:
				throw new CommandExecutionException(this, "This command is not supported for dialog fragment screen.");

			default:
				throw new CommandExecutionException(this, "Screen " + mScreenClass.getSimpleName() + " is not registered.");
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(navigationContext.getActivity(), navigationFactory);
		Class<? extends Screen> screenClassTo = mScreenClass;
		return navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, true, mAnimationData);
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext, Fragment currentFragment) {
		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = mScreenClass;
		return navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, false, mAnimationData);
	}
}
