package me.aartikov.alligator.exceptions;


/**
 * Exception thrown when a navigation error has occurred.
 */
public abstract class NavigationException extends Exception {
	public NavigationException(String message) {
		super(message);
	}
}