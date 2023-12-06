package me.aartikov.alligator.listeners;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.Screen;


/**
 * Screen switching listener that does nothing.
 */
public class DefaultScreenSwitchingListener implements ScreenSwitchingListener {
	@Override
	public void onScreenSwitched(@Nullable Screen screenFrom, @NonNull Screen screenTo) {
	}
}
