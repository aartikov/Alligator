package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.NotSupportedOperationException;
import me.aartikov.alligator.navigators.ActivityNavigator;
import me.aartikov.alligator.navigators.DialogFragmentNavigator;
import me.aartikov.alligator.navigators.FragmentNavigator;

/**
 * Command implementation for {@code goBackTo} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class BackToScreenCommand extends BaseCommand {
	private Screen mScreen;
	@Nullable
	private ScreenResult mScreenResult;
	@Nullable
	private AnimationData mAnimationData;

	public BackToScreenCommand(@NonNull Screen screen, @Nullable ScreenResult screenResult, @Nullable AnimationData animationData) {
		super(screen.getClass());
		mScreen = screen;
		mScreenResult = screenResult;
		mAnimationData = animationData;
	}

	@Override
	protected void executeForActivity(@NonNull ActivityDestination destination, @NonNull ActivityNavigator activityNavigator) throws NavigationException {
		throw new NotSupportedOperationException("BackToScreen command is not supported for activity.");
	}

	@Override
	protected void executeForFragment(@NonNull FragmentDestination destination, @NonNull FragmentNavigator fragmentNavigator) throws NavigationException {
		fragmentNavigator.goBackTo(mScreen, destination, mScreenResult, mAnimationData);
	}

	@Override
	protected void executeForDialogFragment(@NonNull DialogFragmentDestination destination, @NonNull DialogFragmentNavigator dialogFragmentNavigator) throws NavigationException {
		throw new NotSupportedOperationException("BackToScreen command is not supported for dialog fragments.");
	}
}