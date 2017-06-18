package me.aartikov.alligator.exceptions;

/**
 * Date: 13.05.2017
 * Time: 15:14
 *
 * @author Artur Artikov
 */

/**
 * Exception thrown when a screen switching error has occurred.
 */
public class ScreenSwitchingException extends Exception {
	public ScreenSwitchingException(String message) {
		super(message);
	}
}
