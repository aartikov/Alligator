package com.art.alligator;

/**
 * Date: 11.02.2017
 * Time: 11:25
 *
 * @author Artur Artikov
 */

public interface Navigator {
	/**
	 * Add a new screen and go to it
	 */
	void goForward(Screen screen);

	/**
	 * Finish current screen and go back to the previous screen
	 */
	void goBack();

	/**
	 * Go back to a screen with the given class
	 */
	void goBackTo(Class<? extends Screen> screenClass);

	/**
	 * Replace the last screen with a new screen
	 */
	void replace(Screen screen);

	/**
	 * Replace all other screens with a new screen
	 */
	void resetTo(Screen screen);

	/**
	 * Finish a last screen or a group of screens executing some common task
	 */
	void finish();

	/**
	 * Switch screens by name
	 */
	void switchTo(String screenName);
}
