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
	 * @param screenFrom screen that disappears during a switching or {@code null} if there was no current screen before switching
	 * @param screenTo   screen that appears during a switching
	 */
	void onScreenSwitched(@Nullable Screen screenFrom, Screen screenTo);
}