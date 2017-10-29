package me.aartikov.alligator.listeners;

/**
 * Date: 12.03.2017
 * Time: 10:50
 *
 * @author Artur Artikov
 */

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;

/**
 * Interface for screen result handling.
 */
public interface ScreenResultListener {
	/**
	 * Is called when a result of a screen is handled.
	 *
	 * @param screenClass screen class
	 * @param result      screen result
	 */
	void onScreenResult(Class<? extends Screen> screenClass, ScreenResult result);

	/**
	 * Is called when a screen has finished without no result.
	 *
	 * @param screenClass screen class
	 */
	void onCancelled(Class<? extends Screen> screenClass);
}
