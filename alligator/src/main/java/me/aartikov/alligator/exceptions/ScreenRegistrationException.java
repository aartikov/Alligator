package me.aartikov.alligator.exceptions;


/**
 * Exception thrown when an error caused by incorrect screen registration has occurred.
 */
public class ScreenRegistrationException extends NavigationException {
	public ScreenRegistrationException(String message) {
		super(message);
	}
}
