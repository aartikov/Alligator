package com.art.alligator.implementation.commands;

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
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		ScreenSwitcher screenSwitcher = navigationContext.getScreenSwitcher();
		if (screenSwitcher == null) {
			throw new IllegalStateException("Failed to switch screen. ScreenSwitcher is not binded.");
		}

		boolean success = screenSwitcher.switchTo(mScreenName);
		if(!success) {
			throw new RuntimeException("Failed to switch to " + mScreenName);
		}
		return true;
	}
}
