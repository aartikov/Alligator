package com.art.alligator.implementation.commands;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Command;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionAnimationDirection;
import com.art.alligator.implementation.CommandUtils;
import com.art.alligator.implementation.ScreenUtils;

/**
 * Date: 29.12.2016
 * Time: 10:56
 *
 * @author Artur Artikov
 */
public class BackCommand implements Command {
	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		if(navigationContext.getFragmentManager() == null || CommandUtils.getFragmentCount(navigationContext) <= 1) {
			Activity activity = navigationContext.getActivity();
			activity.finish();
			CommandUtils.applyActivityAnimation(activity, getActivityAnimation(navigationContext));
			return false;
		} else {
			FragmentManager fragmentManager = navigationContext.getFragmentManager();
			List<Fragment> fragments = CommandUtils.getFragments(navigationContext);
			Fragment currentFragment = fragments.get(fragments.size() - 1);
			Fragment previousFragment = fragments.get(fragments.size() - 2);

			FragmentTransaction transaction = fragmentManager.beginTransaction();
			CommandUtils.applyFragmentAnimation(transaction, getFragmentAnimation(navigationContext));
			transaction.remove(currentFragment);
			transaction.attach(previousFragment);
			transaction.commitNow();
			return true;
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext) {
		Class<? extends Screen> screenClass = ScreenUtils.getScreenClass(navigationContext.getActivity());
		return navigationContext.getAnimationProvider().getAnimation(TransitionAnimationDirection.BACK, true, screenClass);
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext) {
		Class<? extends Screen> screenClass = ScreenUtils.getScreenClass(CommandUtils.getCurrentFragment(navigationContext));
		return navigationContext.getAnimationProvider().getAnimation(TransitionAnimationDirection.BACK, false, screenClass);
	}
}