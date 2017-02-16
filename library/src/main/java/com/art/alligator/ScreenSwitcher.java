package com.art.alligator;

/**
 * Date: 22.01.2017
 * Time: 22:46
 *
 * @author Artur Artikov
 */
public interface ScreenSwitcher {
	/**
	 * Switch to the screen with the given name
	 * @return true on success, false on fail
	 */
	boolean switchTo(String screenName);
}