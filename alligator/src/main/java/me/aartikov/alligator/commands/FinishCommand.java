package me.aartikov.alligator.commands;

import android.app.Activity;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.exceptions.CommandExecutionException;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;

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
			setActivityResult(activity, navigationFactory);
		}
		Class<? extends Screen> screenClassFrom = navigationFactory.getScreenClass(activity);
		Class<? extends Screen> screenClassTo = navigationFactory.getPreviousScreenClass(activity);
		TransitionAnimation animation = TransitionAnimation.DEFAULT;
		if (screenClassFrom != null && screenClassTo != null) {
			animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, true, mAnimationData);
		}

		navigationContext.getActivityHelper().finish(animation);
		navigationContext.getTransitionListener().onScreenTransition(TransitionType.BACK, screenClassFrom, screenClassTo, true);
		return false;
	}

	private void setActivityResult(Activity activity, NavigationFactory navigationFactory) throws CommandExecutionException {
		Class<? extends Screen> screenClass = navigationFactory.getScreenClass(activity);
		if (screenClass == null) {
			throw new CommandExecutionException(this, "Failed to get a screen class for " + activity.getClass().getSimpleName());
		}

		ActivityScreenImplementation activityScreenImplementation = (ActivityScreenImplementation) navigationFactory.getScreenImplementation(screenClass);
		if (activityScreenImplementation == null) {
			throw new CommandExecutionException(this, "Failed to get a screen implementation for " + screenClass.getSimpleName());
		}

		if (activityScreenImplementation.getScreenResultClass() == null) {
			throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't return a result.");
		}

		Class<? extends ScreenResult> supportedScreenResultClass = activityScreenImplementation.getScreenResultClass();
		if (!supportedScreenResultClass.isAssignableFrom(mScreenResult.getClass())) {
			throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't return a result of class " + mScreenResult.getClass().getCanonicalName() +
			                                          ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		ActivityResult activityResult = activityScreenImplementation.createActivityResult(mScreenResult);
		activity.setResult(activityResult.getResultCode(), activityResult.getIntent());
	}
}
