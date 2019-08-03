package me.aartikov.alligator.commands;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.DialogAnimation;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.exceptions.ActivityResolvingException;
import me.aartikov.alligator.exceptions.MissingFragmentStackException;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.helpers.ActivityHelper;
import me.aartikov.alligator.helpers.DialogFragmentHelper;
import me.aartikov.alligator.helpers.FragmentStack;
import me.aartikov.alligator.navigationfactories.NavigationFactory;


/**
 * Command implementation for {@code reset} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class ResetCommand extends BaseCommand {
	private Screen mScreen;
	@Nullable
	private AnimationData mAnimationData;

	public ResetCommand(@NonNull Screen screen, @Nullable AnimationData animationData) {
		super(screen.getClass());
		mScreen = screen;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(@NonNull ActivityDestination destination, @NonNull NavigationContext navigationContext, @NonNull NavigationFactory navigationFactory) throws NavigationException {
		Activity activity = navigationContext.getActivity();
		Intent intent = destination.createIntent(activity, mScreen, null);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

		ActivityHelper activityHelper = navigationContext.getActivityHelper();
		if (!activityHelper.resolve(intent)) {
			throw new ActivityResolvingException(mScreen);
		}

		Class<? extends Screen> screenClassFrom = navigationFactory.getScreenClass(activity);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		TransitionAnimation animation = TransitionAnimation.DEFAULT;
		if (screenClassFrom != null) {
			animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.RESET, DestinationType.ACTIVITY, screenClassFrom, screenClassTo, mAnimationData);
		}

		activityHelper.start(intent, animation);
		navigationContext.getTransitionListener().onScreenTransition(TransitionType.RESET, DestinationType.ACTIVITY, screenClassFrom, screenClassTo);
		return false;
	}

	@Override
	public boolean execute(@NonNull FragmentDestination destination, @NonNull NavigationContext navigationContext, @NonNull NavigationFactory navigationFactory) throws NavigationException {
		if (navigationContext.getFragmentStack() == null) {
			throw new MissingFragmentStackException("ContainerId is not set.");
		}

		Fragment fragment = destination.createFragment(mScreen);
		FragmentStack fragmentStack = navigationContext.getFragmentStack();
		Fragment currentFragment = fragmentStack.getCurrentFragment();

		Class<? extends Screen> screenClassFrom = currentFragment == null ? null : navigationFactory.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = mScreen.getClass();
		TransitionAnimation animation = TransitionAnimation.DEFAULT;
		if (screenClassFrom != null) {
			animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.RESET, DestinationType.FRAGMENT, screenClassFrom, screenClassTo, mAnimationData);
		}

		fragmentStack.reset(fragment, animation);
		navigationContext.getTransitionListener().onScreenTransition(TransitionType.RESET, DestinationType.FRAGMENT, screenClassFrom, screenClassTo);
		return true;
	}

	@Override
	public boolean execute(@NonNull DialogFragmentDestination destination, @NonNull NavigationContext navigationContext, @NonNull NavigationFactory navigationFactory) throws NavigationException {
		DialogFragmentHelper dialogFragmentHelper = navigationContext.getDialogFragmentHelper();
		while (dialogFragmentHelper.isDialogVisible()) {
			dialogFragmentHelper.hideDialog();
		}

		DialogFragment dialogFragment = destination.createDialogFragment(mScreen);
		DialogAnimation animation = navigationContext.getDialogAnimationProvider().getAnimation(mScreen.getClass(), mAnimationData);
		dialogFragmentHelper.showDialog(dialogFragment, animation);
		navigationContext.getDialogShowingListener().onDialogShown(mScreen.getClass());
		return true;
	}
}
