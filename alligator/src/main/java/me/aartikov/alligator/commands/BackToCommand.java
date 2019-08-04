package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.exceptions.MissingFragmentNavigatorException;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.NotSupportedOperationException;


/**
 * Command implementation for {@code goBackTo} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class BackToCommand extends BaseCommand {
	private Class<? extends Screen> mScreenClass;
	@Nullable
	private ScreenResult mScreenResult;
	@Nullable
	private AnimationData mAnimationData;

	public BackToCommand(@NonNull Class<? extends Screen> screenClass, @Nullable ScreenResult screenResult, @Nullable AnimationData animationData) {
		super(screenClass);
		mScreenClass = screenClass;
		mScreenResult = screenResult;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(@NonNull ActivityDestination destination, @NonNull NavigationContext navigationContext) throws NavigationException {
		navigationContext.getActivityNavigator().goBackTo(mScreenClass, destination, mScreenResult, mAnimationData);
		return false;
	}

	@Override
	public boolean execute(@NonNull FragmentDestination destination, @NonNull NavigationContext navigationContext) throws NavigationException {
		if (navigationContext.getFragmentNavigator() == null) {
			throw new MissingFragmentNavigatorException();
		}
		navigationContext.getFragmentNavigator().goBackTo(mScreenClass, destination, mScreenResult, mAnimationData);
		return true;
	}

	@Override
	public boolean execute(@NonNull DialogFragmentDestination destination, @NonNull NavigationContext navigationContext) throws NavigationException {
		throw new NotSupportedOperationException("BackTo command is not supported for dialog fragments.");
	}
}
