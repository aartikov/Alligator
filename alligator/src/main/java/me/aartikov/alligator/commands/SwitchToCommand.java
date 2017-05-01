package me.aartikov.alligator.commands;

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

	public SwitchToCommand(String screenName) {
		mScreenName = screenName;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		ScreenSwitcher screenSwitcher = navigationContext.getScreenSwitcher();
		if (screenSwitcher == null) {
			throw new CommandExecutionException(this, "ScreenSwitcher is not set.");
		}

		String previousScreenName = screenSwitcher.getCurrentScreenName();
		boolean success = screenSwitcher.switchTo(mScreenName);
		if (!success) {
			throw new CommandExecutionException(this, "Unknown screen name " + mScreenName);
		}
		navigationContext.getNavigationListener().onScreenSwitched(previousScreenName, mScreenName);
		return true;
	}
}
