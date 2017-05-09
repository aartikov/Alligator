package me.aartikov.alligator.commands;

import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.Command;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationFactory;
import me.aartikov.alligator.ScreenSwitcher;
import me.aartikov.alligator.exceptions.CommandExecutionException;

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
	private String mScreenName;
	private AnimationData mAnimationData;

	public SwitchToCommand(String screenName, AnimationData animationData) {
		mScreenName = screenName;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		ScreenSwitcher screenSwitcher = navigationContext.getScreenSwitcher();
		if (screenSwitcher == null) {
			throw new CommandExecutionException(this, "ScreenSwitcher is not set.");
		}

		String previousScreenName = screenSwitcher.getCurrentScreenName();
		if(previousScreenName != null && previousScreenName.equals(mScreenName)) {
			return true;
		}

		boolean success = screenSwitcher.switchTo(mScreenName, mAnimationData);
		if (!success) {
			throw new CommandExecutionException(this, "Unknown screen name " + mScreenName);
		}
		navigationContext.getScreenSwitchingListener().onScreenSwitched(previousScreenName, mScreenName);
		return true;
	}
}
