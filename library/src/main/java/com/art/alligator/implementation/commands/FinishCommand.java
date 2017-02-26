package com.art.alligator.implementation.commands;

import android.app.Activity;

import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Command;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionAnimationDirection;
import com.art.alligator.implementation.CommandUtils;
import com.art.alligator.implementation.ScreenUtils;

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
		CommandUtils.applyActivityAnimation(activity, getActivityAnimation(navigationContext));
		return false;
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext) {
		Class<? extends Screen> screenClass = ScreenUtils.getScreenClass(navigationContext.getActivity());
		return navigationContext.getAnimationProvider().getAnimation(TransitionAnimationDirection.BACK, true, screenClass);
	}
}
