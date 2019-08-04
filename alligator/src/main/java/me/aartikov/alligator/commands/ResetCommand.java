package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.exceptions.MissingFragmentNavigatorException;
import me.aartikov.alligator.exceptions.NavigationException;


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
	public boolean execute(@NonNull ActivityDestination destination, @NonNull NavigationContext navigationContext) throws NavigationException {
		navigationContext.getActivityNavigator().reset(mScreen, destination, mAnimationData);
		return false;
	}

	@Override
	public boolean execute(@NonNull FragmentDestination destination, @NonNull NavigationContext navigationContext) throws NavigationException {
		if (navigationContext.getFragmentNavigator() == null) {
			throw new MissingFragmentNavigatorException();
		}
		navigationContext.getFragmentNavigator().reset(mScreen, destination, mAnimationData);
		return true;
	}

	@Override
	public boolean execute(@NonNull DialogFragmentDestination destination, @NonNull NavigationContext navigationContext) throws NavigationException {
		navigationContext.getDialogFragmentNavigator().reset(mScreen, destination, mAnimationData);
		return true;
	}
}
