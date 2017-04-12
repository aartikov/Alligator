package me.aartikov.alligator.exceptions;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.Command;

/**
 * Date: 12.03.2017
 * Time: 15:43
 *
 * @author Artur Artikov
 */

/**
 * Exception thrown when an implicit intent can't be resolved.
 */
public class FailedResolveActivityException extends CommandExecutionException {
	private Screen mScreen;


	public FailedResolveActivityException(Command command, Screen screen) {
		super(command, "Failed to resolve an activity for a screen " + screen.getClass().getSimpleName());
		mScreen = screen;
	}

	public Screen getScreen() {
		return mScreen;
	}
}
