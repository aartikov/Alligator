package me.aartikov.alligator.exceptions;


/**
 * Exception thrown when unsupported operation is requested.
 */
public class NotSupportedOperationException extends NavigationException {
	public NotSupportedOperationException(String message) {
		super(message);
	}
}
