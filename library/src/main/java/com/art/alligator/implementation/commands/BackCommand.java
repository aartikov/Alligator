package com.art.alligator.implementation.commands;
import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.art.alligator.TransitionAnimation;
import com.art.alligator.AnimationProvider;
import com.art.alligator.NavigationFactory;
import com.art.alligator.implementation.Command;
import com.art.alligator.NavigationContext;

/**
 * Date: 29.12.2016
 * Time: 10:56
 *
 * @author Artur Artikov
 */
public class BackCommand implements Command {
	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Activity activity = navigationContext.getActivity();
		FragmentManager fragmentManager = navigationContext.getFragmentManager();

		if(fragmentManager == null || fragmentManager.getBackStackEntryCount() <= 1) {
			activity.finish();
			AnimationProvider animationProvider = navigationContext.getAnimationProvider();
			TransitionAnimation animation = animationProvider.getActivityBackAnimation(activity.getClass());
			if(animation != null && !animation.equals(TransitionAnimation.DEFAULT)) {
				activity.overridePendingTransition(animation.getEnterAnimation(), animation.getExitAnimation());
			}
			return false;
		} else {
			fragmentManager.popBackStackImmediate();
			return true;
		}
	}
}
