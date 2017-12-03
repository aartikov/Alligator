package me.aartikov.alligator.commands;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.DialogAnimation;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.ActivityResolvingException;
import me.aartikov.alligator.helpers.ActivityHelper;
import me.aartikov.alligator.helpers.FragmentStack;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;

/**
 * Date: 29.12.2016
 * Time: 10:22
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code goForward} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class ForwardCommand extends VisitorCommand {
	private Screen mScreen;
	private AnimationData mAnimationData;

	public ForwardCommand(Screen screen, AnimationData animationData) {
		super(screen.getClass());
		mScreen = screen;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(ActivityScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException {
		Activity activity = navigationContext.getActivity();
		Class<? extends Screen> previousScreenClass = navigationFactory.getScreenClass(activity);
		Intent intent = screenImplementation.createIntent(activity, mScreen, previousScreenClass);

		ActivityHelper activityHelper = navigationContext.getActivityHelper();
		if (!activityHelper.resolve(intent)) {
			throw new ActivityResolvingException(mScreen);
		}

		Class<? extends Screen> screenClassFrom = navigationFactory.getScreenClass(activity);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		TransitionAnimation animation = TransitionAnimation.DEFAULT;
		if (screenClassFrom != null) {
			animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.FORWARD, screenClassFrom, screenClassTo, true, mAnimationData);
		}

		if (screenImplementation.getScreenResultClass() != null) {
			activityHelper.startForResult(intent, screenImplementation.getRequestCode(), animation);
		} else {
			activityHelper.start(intent, animation);
		}

		navigationContext.getTransitionListener().onScreenTransition(TransitionType.FORWARD, screenClassFrom, screenClassTo, true);
		return false;
	}

	@Override
	public boolean execute(FragmentScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException {
		if (navigationContext.getFragmentStack() == null) {
			throw new NavigationException("ContainerId is not set.");
		}

		Fragment fragment = screenImplementation.createFragment(mScreen);
		if (fragment instanceof DialogFragment) {
			throw new NavigationException("DialogFragment is used as usual Fragment.");
		}

		FragmentStack fragmentStack = navigationContext.getFragmentStack();
		Fragment currentFragment = fragmentStack.getCurrentFragment();

		Class<? extends Screen> screenClassFrom = currentFragment == null ? null : navigationFactory.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		TransitionAnimation animation = TransitionAnimation.DEFAULT;
		if (screenClassFrom != null) {
			animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.FORWARD, screenClassFrom, screenClassTo, false, mAnimationData);
		}

		fragmentStack.push(fragment, animation);
		navigationContext.getTransitionListener().onScreenTransition(TransitionType.FORWARD, screenClassFrom, screenClassTo, false);
		return true;
	}

	@Override
	public boolean execute(DialogFragmentScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException {
		DialogFragment dialogFragment = screenImplementation.createDialogFragment(mScreen);
		DialogAnimation animation = navigationContext.getDialogAnimationProvider().getAnimation(mScreen.getClass(), mAnimationData);
		navigationContext.getDialogFragmentHelper().showDialog(dialogFragment, animation);
		navigationContext.getDialogShowingListener().onDialogShown(mScreen.getClass());
		return true;
	}
}
