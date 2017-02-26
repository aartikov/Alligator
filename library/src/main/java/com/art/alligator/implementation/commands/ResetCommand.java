package com.art.alligator.implementation.commands;

import java.util.List;

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
 * Time: 14:30
 *
 * @author Artur Artikov
 */
public class ResetCommand implements Command {
	private Screen mScreen;

	public ResetCommand(Screen screen) {
		mScreen = screen;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Intent intent = navigationFactory.createActivityIntent(navigationContext.getActivity(), mScreen);
		Fragment fragment = navigationFactory.createFragment(mScreen);

		if(intent != null) {
			Activity activity = navigationContext.getActivity();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			ScreenUtils.putScreenClass(intent, mScreen.getClass());
			activity.startActivity(intent);
			CommandUtils.applyActivityAnimation(activity, getActivityAnimation(navigationContext));
			return false;
		} else if (fragment != null) {
			FragmentManager fragmentManager = navigationContext.getFragmentManager();
			if (fragmentManager == null) {
				throw new IllegalStateException("Failed to reset fragments. FragmentManager is not bound.");
			}

			FragmentTransaction transaction = fragmentManager.beginTransaction();
			List<Fragment> fragments = CommandUtils.getFragments(navigationContext);
			for(int i = 0; i < fragments.size(); i++) {
				if(i == fragments.size() - 1) {
					CommandUtils.applyFragmentAnimation(transaction, getFragmentAnimation(navigationContext));
				}
				transaction.remove(fragments.get(i));
			}

			ScreenUtils.putScreenClass(fragment, mScreen.getClass());
			String tag = CommandUtils.getFragmentTag(navigationContext, 0);
			transaction.add(navigationContext.getContainerId(), fragment, tag);
			transaction.commitNow();
			return true;
		} else {
			throw new RuntimeException("Screen " + mScreen.getClass().getSimpleName() + " is not registered.");
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext) {
		return navigationContext.getAnimationProvider().getAnimation(TransitionAnimationDirection.REPLACE, true, mScreen.getClass());
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext) {
		return navigationContext.getAnimationProvider().getAnimation(TransitionAnimationDirection.REPLACE, false, mScreen.getClass());
	}
}
