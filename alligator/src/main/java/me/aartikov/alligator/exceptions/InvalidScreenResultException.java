package me.aartikov.alligator.exceptions;

/**
 * Date: 01.02.2017
 * Time: 11:44
 *
 * @author Artur Artikov
 */

/**
 * Exception thrown when a screen is requested to return unsupported result.
 */
public class InvalidScreenResultException extends NavigationException {
	public InvalidScreenResultException(String message) {
		super(message);
	}
}
