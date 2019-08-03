package me.aartikov.alligator.exceptions;


/**
 * Exception thrown when a command requires a {@link me.aartikov.alligator.flowmanagers.FlowManager} but it is not set to {@link me.aartikov.alligator.NavigationContext}.
 */
public class MissingFlowManagerException extends NavigationException {
	public MissingFlowManagerException(String message) {
		super(message);
	}
}
