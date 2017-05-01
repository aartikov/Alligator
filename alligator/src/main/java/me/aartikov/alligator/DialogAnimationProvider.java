package me.aartikov.alligator;

/**
 * Date: 26.03.2017
 * Time: 12:37
 *
 * @author Artur Artikov
 */

import android.support.annotation.Nullable;

/**
 * Provider of a {@link DialogAnimation}.
 */
public interface DialogAnimationProvider {
	/**
	 * Called when a {@link DialogAnimation} is needed to show a screen represented by a dialog fragment.
	 *
	 * @param screenClass   a class of a shown screen
	 * @param animationData data for an additional animation configuring
	 * @return an animation that will be used to show a dialog fragment
	 */
	DialogAnimation getAnimation(Class<? extends Screen> screenClass, @Nullable AnimationData animationData);
}
