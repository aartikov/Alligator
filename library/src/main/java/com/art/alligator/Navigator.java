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

	void goForward(Screen screen, TransitionAnimation animation);

	/**
	 * Finish current screen and go back to the previous screen
	 */
	void goBack();

	void goBack(TransitionAnimation animation);

	/**
	 * Go back to a screen with the given class
	 */
	void goBackTo(Class<? extends Screen> screenClass);

	void goBackTo(Class<? extends Screen> screenClass, TransitionAnimation animation);

	/**
	 * Replace the last screen with a new screen
	 */
	void replace(Screen screen);

	void replace(Screen screen, TransitionAnimation animation);

	/**
	 * Remove all other screens and add a new screen
	 */
	void reset(Screen screen);

	void reset(Screen screen, TransitionAnimation animation);

	/**
	 * Finish a last screen or a group of screens executing some common task
	 */
	void finish();

	void finish(TransitionAnimation animation);

	/**
	 * Switch screens by name
	 */
	void switchTo(String screenName);
}
