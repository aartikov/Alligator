package me.aartikov.alligator.exceptions;

/**
 * Date: 01.02.2017
 * Time: 11:44
 *
 * @author Artur Artikov
 */

/**
 * Exception thrown when an error caused by incorrect screen registration has occurred.
 */
public class ScreenRegistrationException extends NavigationException {
	public ScreenRegistrationException(String message) {
		super(message);
	}
}
