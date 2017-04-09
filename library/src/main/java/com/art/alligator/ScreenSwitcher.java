package com.art.alligator;

/**
 * Date: 22.01.2017
 * Time: 22:46
 *
 * @author Artur Artikov
 */

/**
 * Interface for switching screens by its names.
 */
public interface ScreenSwitcher {
	/**
	 * Switches to a screen with the given name
	 *
	 * @param screenName screen name
	 * @return true on success, false on fail
	 */
	boolean switchTo(String screenName);
}