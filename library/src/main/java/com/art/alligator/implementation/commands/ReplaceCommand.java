package com.art.alligator.implementation.commands;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.art.alligator.Command;
import com.art.alligator.CommandExecutionException;
import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionType;
import com.art.alligator.implementation.CommandUtils;
import com.art.alligator.implementation.ScreenUtils;

/**
 * Date: 29.12.2016
 * Time: 11:24
 *
 * @author Artur Artikov
 */
public class ReplaceCommand implements Command {
	private Screen mScreen;
	private TransitionAnimation mAnimation;

	public ReplaceCommand(Screen screen, TransitionAnimation animation) {
		mScreen = screen;
		mAnimation = animation;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		Intent intent = navigationFactory.createActivityIntent(navigationContext.getActivity(), mScreen);
		Fragment fragment = navigationFactory.createFragment(mScreen);

		if(intent != null) {
			Activity activity = navigationContext.getActivity();
			ScreenUtils.putScreenClass(intent, mScreen.getClass());
			activity.startActivity(intent);
			activity.finish();
			CommandUtils.applyActivityAnimation(activity, getActivityAnimation(navigationContext));
			return false;

		} else if (fragment != null) {
			FragmentManager fragmentManager = navigationContext.getFragmentManager();
			if (fragmentManager == null) {
				throw new CommandExecutionException("FragmentManager is not bound.");
			}

			FragmentTransaction transaction = fragmentManager.beginTransaction();
			Fragment currentFragment = CommandUtils.getCurrentFragment(navigationContext);
			if (currentFragment != null) {
				CommandUtils.applyFragmentAnimation(transaction, getFragmentAnimation(navigationContext));
				transaction.remove(currentFragment);
			}

			ScreenUtils.putScreenClass(fragment, mScreen.getClass());
			int fragmentCount = CommandUtils.getFragmentCount(navigationContext);
			int index = fragmentCount == 0 ? 0 : fragmentCount - 1;
			String tag = CommandUtils.getFragmentTag(navigationContext, index);
			transaction.add(navigationContext.getContainerId(), fragment, tag);
			transaction.commitNow();
			return true;

		} else {
			throw new CommandExecutionException("Screen " + mScreen.getClass().getSimpleName() + " is not registered.");
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext) {
		if(mAnimation != null) {
			return mAnimation;
		}

		return navigationContext.getAnimationProvider().getAnimation(TransitionType.REPLACE, true, mScreen.getClass());
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext) {
		if(mAnimation != null) {
			return mAnimation;
		}

		return navigationContext.getAnimationProvider().getAnimation(TransitionType.REPLACE, false, mScreen.getClass());
	}
}