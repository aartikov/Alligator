package com.art.alligator;

/**
 * Date: 12.03.2017
 * Time: 10:50
 *
 * @author Artur Artikov
 */

/**
 * Interface for screen result handling
 */
public interface ScreenResultListener {
	void onScreenResult(Class<? extends Screen> screenClass, ScreenResult result);
}
