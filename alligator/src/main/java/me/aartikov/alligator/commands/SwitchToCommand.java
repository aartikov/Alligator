package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.exceptions.MissingScreenSwitcherException;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.screenswitchers.ScreenSwitcher;


/**
 * Command implementation for {@code switchTo} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class SwitchToCommand implements Command {
	private Screen mScreen;
	@Nullable
	private AnimationData mAnimationData;

	public SwitchToCommand(@NonNull Screen screen, @Nullable AnimationData animationData) {
		mScreen = screen;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(@NonNull NavigationContext navigationContext) throws NavigationException {
		ScreenSwitcher screenSwitcher = navigationContext.getScreenSwitcher();
		if (screenSwitcher == null) {
			throw new MissingScreenSwitcherException("ScreenSwitcher is not set.");
		}

		screenSwitcher.switchTo(mScreen, navigationContext.getScreenSwitchingListener(), mAnimationData);
		return true;
	}
}