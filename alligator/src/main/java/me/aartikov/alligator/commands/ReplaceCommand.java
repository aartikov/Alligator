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
import me.aartikov.alligator.exceptions.CommandExecutionException;
import me.aartikov.alligator.exceptions.FailedResolveActivityException;
import me.aartikov.alligator.helpers.ActivityHelper;
import me.aartikov.alligator.helpers.DialogFragmentHelper;
import me.aartikov.alligator.helpers.FragmentStack;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;

/**
 * Date: 29.12.2016
 * Time: 11:24
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code replace} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class ReplaceCommand extends ScreenImplementationVisitorCommand {
	private Screen mScreen;
	private AnimationData mAnimationData;

	public ReplaceCommand(Screen screen, AnimationData animationData) {
		super(screen.getClass());
		mScreen = screen;
		mAnimationData = animationData;
	}

	@Override public boolean execute(ActivityScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		Activity activity = navigationContext.getActivity();
		Class<? extends Screen> previousScreenClass = navigationFactory.getPreviousScreenClass(activity);
		Intent intent = screenImplementation.createIntent(activity, mScreen, previousScreenClass);

		ActivityHelper activityHelper = navigationContext.getActivityHelper();
		if (!activityHelper.resolve(intent)) {
			throw new FailedResolveActivityException(this, mScreen);
		}

		Class<? extends Screen> screenClassFrom = navigationFactory.getScreenClass(activity);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		TransitionAnimation animation = TransitionAnimation.DEFAULT;
		if (screenClassFrom != null) {
			animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.REPLACE, screenClassFrom, screenClassTo, true, mAnimationData);
		}

		activityHelper.start(intent, animation);
		activityHelper.finish(animation);
		navigationContext.getTransitionListener().onScreenTransition(TransitionType.REPLACE, screenClassFrom, screenClassTo, true);
		return false;
	}

	@Override public boolean execute(FragmentScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		if (navigationContext.getFragmentStack() == null) {
			throw new CommandExecutionException(this, "ContainerId is not set.");
		}

		Fragment fragment = screenImplementation.createFragment(mScreen);
		FragmentStack fragmentStack = navigationContext.getFragmentStack();
		Fragment currentFragment = fragmentStack.getCurrentFragment();

		Class<? extends Screen> screenClassFrom = currentFragment == null ? null : navigationFactory.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		TransitionAnimation animation = TransitionAnimation.DEFAULT;
		if (screenClassFrom != null) {
			animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.REPLACE, screenClassFrom, screenClassTo, false, mAnimationData);
		}

		fragmentStack.replace(fragment, animation);
		navigationContext.getTransitionListener().onScreenTransition(TransitionType.REPLACE, screenClassFrom, screenClassTo, false);
		return true;
	}

	@Override public boolean execute(DialogFragmentScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		DialogFragmentHelper dialogFragmentHelper = navigationContext.getDialogFragmentHelper();
		if (dialogFragmentHelper.isDialogVisible()) {
			dialogFragmentHelper.hideDialog();
		}

		DialogFragment dialogFragment = screenImplementation.createDialogFragment(mScreen);
		DialogAnimation animation = navigationContext.getDialogAnimationProvider().getAnimation(mScreen.getClass(), mAnimationData);
		dialogFragmentHelper.showDialog(dialogFragment, animation);
		navigationContext.getDialogShowingListener().onDialogShown(mScreen.getClass());
		return true;
	}
}