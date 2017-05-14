package me.aartikov.alligator.commands;

import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.Command;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenSwitcher;
import me.aartikov.alligator.exceptions.CommandExecutionException;
import me.aartikov.alligator.exceptions.ScreenSwitchingException;

/**
 * Date: 29.12.2016
 * Time: 11:24
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code switchTo} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class SwitchToCommand implements Command {
	private Screen mScreen;
	private AnimationData mAnimationData;

	public SwitchToCommand(Screen screen, AnimationData animationData) {
		mScreen = screen;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		ScreenSwitcher screenSwitcher = navigationContext.getScreenSwitcher();
		if (screenSwitcher == null) {
			throw new CommandExecutionException(this, "ScreenSwitcher is not set.");
		}

		Screen previousScreen = screenSwitcher.getCurrentScreen();
		if(previousScreen != null && previousScreen.equals(mScreen)) {
			return true;
		}

		try {
			screenSwitcher.switchTo(mScreen, mAnimationData);
		} catch (ScreenSwitchingException e) {
			throw new CommandExecutionException(this, e.getMessage());
		}
		navigationContext.getScreenSwitchingListener().onScreenSwitched(previousScreen, mScreen);
		return true;
	}
}
