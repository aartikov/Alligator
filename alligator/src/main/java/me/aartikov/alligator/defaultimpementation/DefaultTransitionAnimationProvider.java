package me.aartikov.alligator.defaultimpementation;

import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionAnimation;
import me.aartikov.alligator.TransitionAnimationProvider;
import me.aartikov.alligator.TransitionType;

/**
 * Date: 24.02.2017
 * Time: 19:14
 *
 * @author Artur Artikov
 */

/**
 * Default implementation of {@link TransitionAnimationProvider}. Always returns {@code TransitionAnimation.DEFAULT}.
 */
public class DefaultTransitionAnimationProvider implements TransitionAnimationProvider {
	@Override
	public TransitionAnimation getAnimation(TransitionType transitionType, Class<? extends Screen> screenClassFrom, Class<? extends Screen> screenClassTo, boolean isActivity, AnimationData animationData) {
		return TransitionAnimation.DEFAULT;
	}
}
