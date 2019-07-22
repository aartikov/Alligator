package me.aartikov.alligator.exceptions;

/**
 * Date: 22.07.2019
 * Time: 14:18
 *
 * @author Terenfear
 */

/**
 * Exception thrown when a command requires a {@link me.aartikov.alligator.flowmanagers.FlowManager} but it is not set to {@link me.aartikov.alligator.NavigationContext}.
 */
public class MissingFlowManagerException extends NavigationException {
	public MissingFlowManagerException(String message) {
		super(message);
	}
}
