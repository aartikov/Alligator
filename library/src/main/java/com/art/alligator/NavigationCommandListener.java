package com.art.alligator;

/**
 * Date: 26.02.2017
 * Time: 18:51
 *
 * @author Artur Artikov
 */

/**
 * Interface for command execution listening
 */
public interface NavigationCommandListener {
	void onNavigationCommandExecuted(Command command);
}
