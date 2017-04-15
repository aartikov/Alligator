package me.aartikov.alligator.defaultimpementation;

import android.support.annotation.Nullable;

import me.aartikov.alligator.NavigationListener;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionType;

/**
 * Date: 15.04.2017
 * Time: 13:23
 *
 * @author Artur Artikov
 */

/**
 * Navigation listener that does nothing.
 */
public class DefaultNavigationListener implements NavigationListener {
	@Override
	public void onScreenTransition(TransitionType transitionType, @Nullable Class<? extends Screen> screenClassFrom, @Nullable Class<? extends Screen> screenClassTo, boolean isActivity) {
	}

	@Override
	public void onDialogShown(Class<? extends Screen> screenClass) {
	}

	@Override
	public void onScreenSwitched(@Nullable String screenNameFrom, String screenNameTo) {
	}
}
