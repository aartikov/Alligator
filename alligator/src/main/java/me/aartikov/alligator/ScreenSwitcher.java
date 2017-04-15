package me.aartikov.alligator;

/**
 * Date: 22.01.2017
 * Time: 22:46
 *
 * @author Artur Artikov
 */

import android.support.annotation.Nullable;

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

	/**
	 * Returns a name of a current screen.
	 *
	 * @return a name of a current screen or {@code null} if there is no current screen.
	 */
	@Nullable String getCurrentScreenName();
}