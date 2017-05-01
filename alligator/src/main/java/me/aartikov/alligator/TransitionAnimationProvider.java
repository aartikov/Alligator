package me.aartikov.alligator;

/**
 * Date: 24.02.2017
 * Time: 19:00
 *
 * @author Artur Artikov
 */

import android.support.annotation.Nullable;

/**
 * Provider of a {@link TransitionAnimation}.
 */
public interface TransitionAnimationProvider {
	/**
	 * Called when a {@link TransitionAnimation} is needed to make a transition from one screen to another.
	 *
	 * @param transitionType  type of transition
	 * @param screenClassFrom class of the screen that disappears during a transition
	 * @param screenClassTo   class of the screen that appears during a transition
	 * @param isActivity      true if the screens involved in the transition are represented by activities
	 * @param animationData   data for an additional animation configuring
	 * @return an animation that will be used for a transition
	 */
	TransitionAnimation getAnimation(TransitionType transitionType, Class<? extends Screen> screenClassFrom, Class<? extends Screen> screenClassTo, boolean isActivity, @Nullable AnimationData animationData);
}
