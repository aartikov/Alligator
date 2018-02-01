package me.aartikov.alligator.exceptions;

/**
 * Date: 01.02.2017
 * Time: 11:44
 *
 * @author Artur Artikov
 */

/**
 * Exception thrown when a command requires a {@link me.aartikov.alligator.helpers.FragmentStack}
 * but it is missing because containerId is not set to {@link me.aartikov.alligator.NavigationContext}.
 */
public class MissingFragmentStackException extends NavigationException {
	public MissingFragmentStackException(String message) {
		super(message);
	}
}
