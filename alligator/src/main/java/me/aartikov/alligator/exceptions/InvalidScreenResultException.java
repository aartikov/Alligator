package me.aartikov.alligator.exceptions;


/**
 * Exception thrown when a screen is requested to return unsupported result.
 */
public class InvalidScreenResultException extends NavigationException {
	public InvalidScreenResultException(String message) {
		super(message);
	}
}
