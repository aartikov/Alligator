package com.art.alligator.exceptions;

import com.art.alligator.Screen;
import com.art.alligator.Command;

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
		super(command, "Failed resolve activity for screen " + screen.getClass().getSimpleName());
		mScreen = screen;
	}

	public Screen getScreen() {
		return mScreen;
	}
}
