package com.art.alligator.commands;

import com.art.alligator.ActivityResult;
import com.art.alligator.AnimationData;
import com.art.alligator.Command;
import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.ScreenResult;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionType;
import com.art.alligator.exceptions.CommandExecutionException;
import com.art.alligator.internal.ActivityHelper;
import com.art.alligator.internal.ScreenClassUtils;

/**
 * Date: 12.02.2017
 * Time: 0:53
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code finish} method and {@code finishWithResult} method of {@link com.art.alligator.AndroidNavigator}.
 */
public class FinishCommand implements Command {
	private ScreenResult mScreenResult;
	private AnimationData mAnimationData;

	public FinishCommand(ScreenResult screenResult, AnimationData animationData) {
		mScreenResult = screenResult;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		if (mScreenResult != null) {
			Class<? extends Screen> screenClass = ScreenClassUtils.getScreenClass(navigationContext.getActivity(), navigationFactory);

			if (!navigationFactory.isScreenForResult(screenClass)) {
				throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " is not registered as screen for result.");
			}

			Class<? extends ScreenResult> registeredScreenResultClass = navigationFactory.getScreenResultClass(screenClass);
			if (!registeredScreenResultClass.isAssignableFrom(mScreenResult.getClass())) {
				throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't finish with result of class " + mScreenResult.getClass().getCanonicalName() +
				                                          ". It is registered to finish with result of class " + registeredScreenResultClass.getCanonicalName());
			}

			ActivityResult activityResult = navigationFactory.createActivityResult(screenClass, mScreenResult);
			navigationContext.getActivity().setResult(activityResult.getResultCode(), activityResult.getIntent());
		}

		ActivityHelper activityHelper = ActivityHelper.from(navigationContext);
		TransitionAnimation animation = getActivityAnimation(navigationContext, navigationFactory);
		activityHelper.finish(animation);
		return false;
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(navigationContext.getActivity(), navigationFactory);
		Class<? extends Screen> screenClassTo = ScreenClassUtils.getPreviousScreenClass(navigationContext.getActivity());
		return navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, true, mAnimationData);
	}
}
