package com.art.alligator.implementation.commands;

import java.util.List;

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
import com.art.alligator.implementation.FragmentStack;
import com.art.alligator.implementation.ScreenClassUtils;

/**
 * Date: 11.02.2017
 * Time: 11:14
 *
 * @author Artur Artikov
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
		if (navigationFactory.isActivityScreen(mScreenClass)) {
			Class activityClass = navigationFactory.getActivityClass(mScreenClass);
			if (activityClass == null) {
				throw new CommandExecutionException(this, "Activity class for " + mScreenClass.getSimpleName() + " is null.");
			}

			Activity activity = navigationContext.getActivity();
			Intent intent = new Intent(activity, activityClass);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			ScreenClassUtils.putScreenClass(intent, mScreenClass);
			activity.startActivity(intent);
			CommandUtils.applyActivityAnimation(activity, getActivityAnimation(navigationContext, navigationFactory));
			return false;

		} else if (navigationFactory.isFragmentScreen(mScreenClass)) {
			if (navigationContext.getFragmentManager() == null) {
				throw new CommandExecutionException(this, "FragmentManager is not bound.");
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
		} else {
			throw new CommandExecutionException(this, "Screen " + mScreenClass.getSimpleName() + " is not registered.");
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(navigationContext.getActivity(), navigationFactory);
		Class<? extends Screen> screenClassTo = mScreenClass;
		return navigationContext.getAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, true, mAnimationData);
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext, Fragment currentFragment) {
		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = mScreenClass;
		return navigationContext.getAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, false, mAnimationData);
	}
}
