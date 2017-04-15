package me.aartikov.alligator;

/**
 * Date: 26.02.2017
 * Time: 18:51
 *
 * @author Artur Artikov
 */

import android.support.annotation.Nullable;

/**
 * Interface for navigation listening.
 */
public interface NavigationListener {
	/**
	 * Called when a transition from one screen to another has been executed.
	 *
	 * @param transitionType  type of a transition
	 * @param screenClassFrom class of the screen that disappears during a transition or {@code null} if there was no current screen before transition
	 * @param screenClassTo   class of the screen that appears during a transition or {@code null} if there was no previous screen before transition
	 * @param isActivity      true if the screens involved in the transition are represented by activities
	 */
	void onScreenTransition(TransitionType transitionType, @Nullable Class<? extends Screen> screenClassFrom, @Nullable Class<? extends Screen> screenClassTo, boolean isActivity);

	/**
	 * Called when a dialog was shown.
	 *
	 * @param screenClass class of a screen that represents a dialog.
	 */
	void onDialogShown(Class<? extends Screen> screenClass);

	/**
	 * Called after a screen has been switched.
	 *
	 * @param screenNameFrom name of the screen that disappears during a switching or {@code null} if there was no current screen before switching
	 * @param screenNameTo   name of the screen that appears during a switching
	 */
	void onScreenSwitched(@Nullable String screenNameFrom, String screenNameTo);
}
