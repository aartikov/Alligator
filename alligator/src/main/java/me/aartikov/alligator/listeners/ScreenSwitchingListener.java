package me.aartikov.alligator.listeners;

/**
 * Date: 09.05.2017
 * Time: 16:11
 *
 * @author Artur Artikov
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.screenswitchers.ScreenSwitcher;

/**
 * Interface for listening of screen switching.
 */
public interface ScreenSwitchingListener {
	/**
	 * Is called after a screen has been switched using {@link ScreenSwitcher}.
	 *
	 * @param screenFrom screen that disappears during a switching or {@code null} if there was no current screen before switching
	 * @param screenTo   screen that appears during a switching
	 */
	void onScreenSwitched(@Nullable Screen screenFrom, @NonNull Screen screenTo);
}