package me.aartikov.alligator;

import me.aartikov.alligator.exceptions.CommandExecutionException;

/**
 * Date: 12.03.2017
 * Time: 15:15
 *
 * @author Artur Artikov
 */

/**
 * Interface for navigation error handling.
 */
public interface NavigationErrorListener {
	/**
	 * Called when a navigation error has occurred.
	 *
	 * @param e exception with information about an error
	 */
	void onNavigationError(CommandExecutionException e);
}
