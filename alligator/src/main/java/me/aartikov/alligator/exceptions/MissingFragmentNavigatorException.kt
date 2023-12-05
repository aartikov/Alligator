package me.aartikov.alligator.exceptions;


import androidx.fragment.app.FragmentManager;

/**
 * Exception thrown when fragment navigation was requested but it was not configured
 * with method {@link me.aartikov.alligator.NavigationContext.Builder#fragmentNavigation(FragmentManager, int)}.
 */
public class MissingFragmentNavigatorException extends NavigationException {
	public MissingFragmentNavigatorException() {
		super("Fragment navigation is not configured. Do you forget to call fragmentNavigation method of NavigationContext.Builder?");
	}
}
