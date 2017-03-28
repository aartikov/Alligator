package com.art.alligator;

/**
 * Date: 24.02.2017
 * Time: 19:00
 *
 * @author Artur Artikov
 */

/**
 * Provider of {@link TransitionAnimation}
 */
public interface TransitionAnimationProvider {
	/**
	 * Returns provided animation
	 */
	TransitionAnimation getAnimation(TransitionType transitionType, Class<? extends Screen> screenClassFrom, Class<? extends Screen> screenClassTo, boolean isActivity, AnimationData animationData);
}
