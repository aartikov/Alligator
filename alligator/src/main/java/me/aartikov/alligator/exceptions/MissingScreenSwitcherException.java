package me.aartikov.alligator.exceptions;

/**
 * Date: 01.02.2017
 * Time: 11:44
 *
 * @author Artur Artikov
 */

/**
 * Exception thrown when a command requires a {@link me.aartikov.alligator.screenswitchers.ScreenSwitcher} but it is not set to {@link me.aartikov.alligator.NavigationContext}.
 */
public class MissingScreenSwitcherException extends NavigationException {
	public MissingScreenSwitcherException(String message) {
		super(message);
	}
}
