package me.aartikov.alligator.exceptions;


import androidx.fragment.app.FragmentManager;

/**
 * Exception thrown when flow fragment navigation was requested but it was not configured
 * with method {@link me.aartikov.alligator.NavigationContext.Builder#flowFragmentNavigation(FragmentManager, int)}.
 */
public class MissingFlowFragmentNavigatorException extends NavigationException {
	public MissingFlowFragmentNavigatorException() {
		super("Flow fragment navigation is not configured. Do you forget to call flowFragmentNavigation method of NavigationContext.Builder?");
	}
}
