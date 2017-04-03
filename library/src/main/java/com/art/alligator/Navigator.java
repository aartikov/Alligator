package com.art.alligator;

/**
 * Date: 11.02.2017
 * Time: 11:25
 *
 * @author Artur Artikov
 */

/**
 * Iterface for navigation
 */
public interface Navigator {
	/**
	 * Adds a new screen and go to it
	 */
	void goForward(Screen screen);

	/**
	 * goForvard with animation data
	 */
	void goForward(Screen screen, AnimationData animationData);

	/**
	 * Finishes current screen and go back to the previous screen
	 */
	void goBack();

	/**
	 * goBack with animation data
	 */
	void goBack(AnimationData animationData);

	/**
	 * Goes back to the screen with the given class
	 */
	void goBackTo(Class<? extends Screen> screenClass);

	/**
	 * goBackTo with animation data
	 */
	void goBackTo(Class<? extends Screen> screenClass, AnimationData animationData);

	/**
	 * Replaces the last screen with a new screen
	 */
	void replace(Screen screen);

	/**
	 * replace with animation data
	 */
	void replace(Screen screen, AnimationData animationData);

	/**
	 * Removes all other screens and add a new screen
	 */
	void reset(Screen screen);

	/**
	 * reset with animation data
	 */
	void reset(Screen screen, AnimationData animationData);

	/**
	 * Finishes a last screen or a group of screens executing some common task
	 */
	void finish();

	/**
	 * finish with animation data
	 */
	void finish(AnimationData animationData);

	/**
	 * Finishes with result
	 */
	void finishWithResult(ScreenResult screenResult);

	/**
	 * finishWithResult with animation data
	 */
	void finishWithResult(ScreenResult screenResult, AnimationData animationData);

	/**
	 * Switches screens by name
	 */
	void switchTo(String screenName);
}
