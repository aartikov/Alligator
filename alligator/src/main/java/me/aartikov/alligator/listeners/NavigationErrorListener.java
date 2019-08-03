package me.aartikov.alligator.listeners;

import androidx.annotation.NonNull;

import me.aartikov.alligator.commands.Command;
import me.aartikov.alligator.exceptions.NavigationException;


/**
 * Interface for navigation error handling.
 */
public interface NavigationErrorListener {
	/**
	 * Is called when an error has occurred during {@link Command} execution.
	 *
	 * @param e exception with information about an error
	 */
	void onNavigationError(@NonNull NavigationException e);
}
