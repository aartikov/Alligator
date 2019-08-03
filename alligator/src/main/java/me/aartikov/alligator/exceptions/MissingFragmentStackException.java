package me.aartikov.alligator.exceptions;


/**
 * Exception thrown when a command requires a {@link me.aartikov.alligator.helpers.FragmentStack}
 * but it is missing because containerId is not set to {@link me.aartikov.alligator.NavigationContext}.
 */
public class MissingFragmentStackException extends NavigationException {
	public MissingFragmentStackException(String message) {
		super(message);
	}
}
