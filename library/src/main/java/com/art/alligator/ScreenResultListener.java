package com.art.alligator;

/**
 * Date: 12.03.2017
 * Time: 10:50
 *
 * @author Artur Artikov
 */

public interface ScreenResultListener {
	boolean onScreenResult(Class<? extends Screen> screenClass, ScreenResult result);
}
