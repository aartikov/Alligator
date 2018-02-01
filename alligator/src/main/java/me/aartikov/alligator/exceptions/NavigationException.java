package me.aartikov.alligator.exceptions;

/**
 * Date: 26.02.2017
 * Time: 18:56
 *
 * @author Artur Artikov
 */

/**
 * Exception thrown when a navigation error has occurred.
 */
public abstract class NavigationException extends Exception {
	public NavigationException(String message) {
		super(message);
	}
}