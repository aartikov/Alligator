package me.aartikov.alligator.listeners;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;

/**
 * Date: 29.10.2017
 * Time: 12:56
 *
 * @author Artur Artikov
 */

public class DefaultScreenResultListener implements ScreenResultListener {
	@Override public void onScreenResult(Class<? extends Screen> screenClass, ScreenResult result) {
	}

	@Override public void onCancelled(Class<? extends Screen> screenClass) {
	}
}