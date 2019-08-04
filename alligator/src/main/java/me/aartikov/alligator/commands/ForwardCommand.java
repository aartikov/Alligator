package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.navigators.ActivityNavigator;
import me.aartikov.alligator.navigators.DialogFragmentNavigator;
import me.aartikov.alligator.navigators.FragmentNavigator;


/**
 * Command implementation for {@code goForward} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class ForwardCommand extends BaseCommand {
	private Screen mScreen;
	@Nullable
	private AnimationData mAnimationData;

	public ForwardCommand(@NonNull Screen screen, @Nullable AnimationData animationData) {
		super(screen.getClass());
		mScreen = screen;
		mAnimationData = animationData;
	}

	@Override
	protected void executeForActivity(@NonNull ActivityDestination destination, @NonNull ActivityNavigator activityNavigator) throws NavigationException {
		activityNavigator.goForward(mScreen, destination, mAnimationData);
	}

	@Override
	protected void executeForFragment(@NonNull FragmentDestination destination, @NonNull FragmentNavigator fragmentNavigator) throws NavigationException {
		fragmentNavigator.goForward(mScreen, destination, mAnimationData);
	}

	@Override
	protected void executeForDialogFragment(@NonNull DialogFragmentDestination destination, @NonNull DialogFragmentNavigator dialogFragmentNavigator) throws NavigationException {
		dialogFragmentNavigator.goForward(mScreen, destination, mAnimationData);
	}
}
