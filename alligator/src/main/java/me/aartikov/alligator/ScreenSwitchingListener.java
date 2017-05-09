package me.aartikov.alligator;

/**
 * Date: 09.05.2017
 * Time: 16:11
 *
 * @author Artur Artikov
 */

import android.support.annotation.Nullable;

/**
 * Interface for listening of screen switching.
 */
public interface ScreenSwitchingListener {
	/**
	 * Called after a screen has been switched using {@link ScreenSwitcher}.
	 *
	 * @param screenNameFrom name of the screen that disappears during a switching or {@code null} if there was no current screen before switching
	 * @param screenNameTo   name of the screen that appears during a switching
	 */
	void onScreenSwitched(@Nullable String screenNameFrom, String screenNameTo);
}
