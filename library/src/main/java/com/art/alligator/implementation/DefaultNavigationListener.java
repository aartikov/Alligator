package com.art.alligator.implementation;

import com.art.alligator.Command;
import com.art.alligator.NavigationListener;

/**
 * Date: 26.02.2017
 * Time: 18:53
 *
 * @author Artur Artikov
 */

public class DefaultNavigationListener implements NavigationListener {
	@Override
	public void onExecuted(Command command) {
		// nothing
	}

	@Override
	public void onError(Command command, String message) {
		throw new RuntimeException("Failed to execute navigation command " + command.getClass().getSimpleName() + ". " + message);
	}
}
