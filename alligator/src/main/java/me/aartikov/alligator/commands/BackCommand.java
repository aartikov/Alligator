package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.exceptions.NavigationException;


/**
 * Command implementation for {@code goBack} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class BackCommand implements Command {
	@Nullable
	private ScreenResult mScreenResult;
	@Nullable
	private AnimationData mAnimationData;

	public BackCommand(@Nullable ScreenResult screenResult, @Nullable AnimationData animationData) {
		mScreenResult = screenResult;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(@NonNull NavigationContext navigationContext) throws NavigationException {
		if (navigationContext.getDialogFragmentNavigator().canGoBack()) {
			navigationContext.getDialogFragmentNavigator().goBack(mScreenResult);
			return true;
		} else if (navigationContext.getFragmentNavigator() != null && navigationContext.getFragmentNavigator().canGoBack()) {
			navigationContext.getFragmentNavigator().goBack(mScreenResult, mAnimationData);
			return true;
		} else {
			navigationContext.getActivityNavigator().goBack(mScreenResult, mAnimationData);
			return false;
		}
	}
}
