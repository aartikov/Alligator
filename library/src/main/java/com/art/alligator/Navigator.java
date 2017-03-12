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

	void goForward(Screen screen, AnimationData animationData);

	/**
	 * Go forward for result
	 */
	void goForwardForResult(Screen screen);

	void goForwardForResult(Screen screen, AnimationData animationData);

	/**
	 * Finish current screen and go back to the previous screen
	 */
	void goBack();

	void goBack(AnimationData animationData);

	/**
	 * Go back to a screen with the given class
	 */
	void goBackTo(Class<? extends Screen> screenClass);

	void goBackTo(Class<? extends Screen> screenClass, AnimationData animationData);

	/**
	 * Replace the last screen with a new screen
	 */
	void replace(Screen screen);

	void replace(Screen screen, AnimationData animationData);

	/**
	 * Remove all other screens and add a new screen
	 */
	void reset(Screen screen);

	void reset(Screen screen, AnimationData animationData);

	/**
	 * Finish a last screen or a group of screens executing some common task
	 */
	void finish();

	void finish(AnimationData animationData);

	/**
	 * Finish a last screen or a group of screens executing some common task
	 */
	void finishWithResult(ScreenResult screenResult);

	void finishWithResult(ScreenResult screenResult, AnimationData animationData);

	/**
	 * Switch screens by name
	 */
	void switchTo(String screenName);
}
