package me.aartikov.alligator;

/**
 * Date: 12.03.2017
 * Time: 10:50
 *
 * @author Artur Artikov
 */

import android.support.annotation.Nullable;

/**
 * Interface for screen result handling.
 */
public interface ScreenResultListener {
	/**
	 * Is called when a result of a screen is handled.
	 *
	 * @param screenClass screen class
	 * @param result      screen result. Can be {@code null} when a screen has finished without no result.
	 */
	void onScreenResult(Class<? extends Screen> screenClass, @Nullable ScreenResult result);
}
