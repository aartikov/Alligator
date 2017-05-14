package me.aartikov.alligator;

/**
 * Date: 11.02.2017
 * Time: 11:25
 *
 * @author Artur Artikov
 */

/**
 * Interface with navigation methods.
 */
public interface Navigator {
	/**
	 * Adds a new screen and goes to it.
	 *
	 * @param screen new screen
	 */
	void goForward(Screen screen);

	/**
	 * {@code goForward} with an animation data.
	 *
	 * @param screen        new screen
	 * @param animationData animation data for an additional animation configuring
	 */
	void goForward(Screen screen, AnimationData animationData);

	/**
	 * Finishes a current screen and goes back to the previous screen.
	 */
	void goBack();

	/**
	 * {@code goBack} with an animation data.
	 *
	 * @param animationData animation data for an additional animation configuring
	 */
	void goBack(AnimationData animationData);

	/**
	 * Goes back to a screen with the given class.
	 *
	 * @param screenClass screen class for going back
	 */
	void goBackTo(Class<? extends Screen> screenClass);

	/**
	 * {@code goBackTo} with an animation data.
	 *
	 * @param screenClass   screen class for going back
	 * @param animationData animation data for an additional animation configuring
	 */
	void goBackTo(Class<? extends Screen> screenClass, AnimationData animationData);

	/**
	 * Replaces the last screen with a new screen.
	 *
	 * @param screen new screen
	 */
	void replace(Screen screen);

	/**
	 * {@code replace} with an animation data.
	 *
	 * @param screen        new screen
	 * @param animationData animation data for an additional animation configuring
	 */
	void replace(Screen screen, AnimationData animationData);

	/**
	 * Removes all other screens and adds a new screen.
	 *
	 * @param screen new screen
	 */
	void reset(Screen screen);

	/**
	 * {@code reset} with an animation data.
	 *
	 * @param screen        new screen
	 * @param animationData animation data for an additional animation configuring
	 */
	void reset(Screen screen, AnimationData animationData);

	/**
	 * Finishes a last screen or a group of screens executing some common task.
	 */
	void finish();

	/**
	 * {@code finish} with an animation data.
	 *
	 * @param animationData animation data for an additional animation configuring
	 */
	void finish(AnimationData animationData);

	/**
	 * Finishes a last screen or a group of screens executing some common task and returns a screen result.
	 *
	 * @param screenResult screen result that will be returned
	 */
	void finishWithResult(ScreenResult screenResult);

	/**
	 * {@code finishWithResult} with an animation data.
	 *
	 * @param screenResult  screen result that will be returned
	 * @param animationData animation data for an additional animation configuring
	 */
	void finishWithResult(ScreenResult screenResult, AnimationData animationData);

	/**
	 * Switches screen.
	 *
	 * @param screen screen for switching to. A screen must have {@code equals()} and {@code hashCode()} correctly overridden because it can be used as a key internally.
	 */
	void switchTo(Screen screen);

	/**
	 * {@code switchTo} with an animation data.
	 *
	 * @param screen         for switching to. A screen must have {@code equals()} and {@code hashCode()} correctly overridden because it can be used as a key internally.
	 * @param animationData animation data for an additional animation configuring
	 */
	void switchTo(Screen screen, AnimationData animationData);
}
