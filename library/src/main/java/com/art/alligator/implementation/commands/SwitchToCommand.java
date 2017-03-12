package com.art.alligator.implementation.commands;

import com.art.alligator.CommandExecutionException;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Command;
import com.art.alligator.NavigationContext;
import com.art.alligator.ScreenSwitcher;

/**
 * Date: 29.12.2016
 * Time: 11:24
 *
 * @author Artur Artikov
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
			throw new CommandExecutionException(this, "ScreenSwitcher is not bound.");
		}

		boolean success = screenSwitcher.switchTo(mScreenName);
		if(!success) {
			throw new CommandExecutionException(this, "Unknown screen name " + mScreenName);
		}
		return true;
	}
}
