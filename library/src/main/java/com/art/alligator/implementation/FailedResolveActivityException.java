package com.art.alligator.implementation;

import com.art.alligator.Command;
import com.art.alligator.CommandExecutionException;
import com.art.alligator.Screen;

/**
 * Date: 12.03.2017
 * Time: 15:43
 *
 * @author Artur Artikov
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
