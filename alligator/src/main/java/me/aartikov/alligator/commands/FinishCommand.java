package me.aartikov.alligator.commands;

import android.app.Activity;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.Command;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.TransitionAnimation;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.exceptions.CommandExecutionException;
import me.aartikov.alligator.internal.ActivityHelper;
import me.aartikov.alligator.internal.ScreenClassUtils;

/**
 * Date: 12.02.2017
 * Time: 0:53
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code finish} method and {@code finishWithResult} method of {@link me.aartikov.alligator.AndroidNavigator}.
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
		Activity activity = navigationContext.getActivity();
		if (mScreenResult != null) {
			Class<? extends Screen> screenClass = ScreenClassUtils.getScreenClass(activity, navigationFactory);

			if (!navigationFactory.isScreenForResult(screenClass)) {
				throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't return a result.");
			}

			Class<? extends ScreenResult> expectedScreenResultClass = navigationFactory.getScreenResultClass(screenClass);
			if (!expectedScreenResultClass.isAssignableFrom(mScreenResult.getClass())) {
				throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't finish with result of class " + mScreenResult.getClass().getCanonicalName() +
				                                          ". It returns a result of class " + expectedScreenResultClass.getCanonicalName());
			}

			ActivityResult activityResult = navigationFactory.createActivityResult(screenClass, mScreenResult);
			activity.setResult(activityResult.getResultCode(), activityResult.getIntent());
		}

		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(activity, navigationFactory);
		Class<? extends Screen> screenClassTo = ScreenClassUtils.getPreviousScreenClass(activity);
		TransitionAnimation animation = TransitionAnimation.DEFAULT;
		if (screenClassFrom != null && screenClassTo != null) {
			animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, true, mAnimationData);
		}

		ActivityHelper activityHelper = ActivityHelper.from(navigationContext);
		activityHelper.finish(animation);
		navigationContext.getNavigationListener().onScreenTransition(TransitionType.BACK, screenClassFrom, screenClassTo, true);
		return false;
	}
}
