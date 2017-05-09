package me.aartikov.alligator;

import android.support.annotation.Nullable;

/**
 * Date: 09.05.2017
 * Time: 16:06
 *
 * @author Artur Artikov
 */

/**
 * Interface for listening of screen transition.
 */
public interface TransitionListener {
	/**
	 * Called when a transition from one screen to another has been executed.
	 *
	 * @param transitionType  type of a transition
	 * @param screenClassFrom class of the screen that disappears during a transition or {@code null} if there was no current screen before transition
	 * @param screenClassTo   class of the screen that appears during a transition or {@code null} if there was no previous screen before transition
	 * @param isActivity      true if the screens involved in the transition are represented by activities
	 */
	void onScreenTransition(TransitionType transitionType, @Nullable Class<? extends Screen> screenClassFrom, @Nullable Class<? extends Screen> screenClassTo, boolean isActivity);
}
