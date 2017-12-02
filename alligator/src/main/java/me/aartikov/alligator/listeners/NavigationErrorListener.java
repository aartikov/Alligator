package me.aartikov.alligator.listeners;

import me.aartikov.alligator.commands.Command;
import me.aartikov.alligator.exceptions.NavigationException;

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
	 * Is called when an error has occurred during {@link Command} execution.
	 *
	 * @param e exception with information about an error
	 */
	void onNavigationError(NavigationException e);
}
