package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.exceptions.NavigationException;


/**
 * Command implementation for {@code finish} method and {@code finishWithResult} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class FinishCommand implements Command {
	@Nullable
	private ScreenResult mScreenResult;
	@Nullable
	private AnimationData mAnimationData;

	public FinishCommand(@Nullable ScreenResult screenResult, @Nullable AnimationData animationData) {
		mScreenResult = screenResult;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(@NonNull NavigationContext navigationContext) throws NavigationException {
		navigationContext.getActivityNavigator().goBack(mScreenResult, mAnimationData);
		return false;
	}
}
