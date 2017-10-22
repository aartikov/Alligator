package me.aartikov.alligator.listeners;

import android.support.annotation.Nullable;

import me.aartikov.alligator.Screen;

/**
 * Date: 09.05.2017
 * Time: 16:22
 *
 * @author Artur Artikov
 */

/**
 * Screen switching listener that does nothing.
 */
public class DefaultScreenSwitchingListener implements ScreenSwitchingListener {
	@Override
	public void onScreenSwitched(@Nullable Screen screenFrom, Screen screenTo) {
	}
}
