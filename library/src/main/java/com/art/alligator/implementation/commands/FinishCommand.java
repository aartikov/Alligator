package com.art.alligator.implementation.commands;

import android.app.Activity;

import com.art.alligator.TransitionAnimation;
import com.art.alligator.AnimationProvider;
import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.implementation.Command;

/**
 * Date: 12.02.2017
 * Time: 0:53
 *
 * @author Artur Artikov
 */

public class FinishCommand implements Command {
	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Activity activity = navigationContext.getActivity();
		activity.finish();
		AnimationProvider animationProvider = navigationContext.getAnimationProvider();
		TransitionAnimation animation = animationProvider.getActivityBackAnimation(activity.getClass());
		if(animation != null && !animation.equals(TransitionAnimation.DEFAULT)) {
			activity.overridePendingTransition(animation.getEnterAnimation(), animation.getExitAnimation());
		}
		return false;
	}
}
