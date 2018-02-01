package me.aartikov.alligator.exceptions;

/**
 * Date: 01.02.2017
 * Time: 11:44
 *
 * @author Artur Artikov
 */

/**
 * Exception thrown when unsupported operation is requested.
 */
public class NotSupportedOperationException extends NavigationException {
	public NotSupportedOperationException(String message) {
		super(message);
	}
}
