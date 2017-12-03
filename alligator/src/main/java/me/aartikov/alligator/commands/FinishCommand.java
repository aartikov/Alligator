package me.aartikov.alligator.commands;

import android.app.Activity;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.navigationfactories.NavigationFactory;

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
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException {
		Activity activity = navigationContext.getActivity();
		if (mScreenResult != null) {
			navigationContext.getScreenResultHelper().setActivityResult(activity, mScreenResult, navigationFactory);
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
}
