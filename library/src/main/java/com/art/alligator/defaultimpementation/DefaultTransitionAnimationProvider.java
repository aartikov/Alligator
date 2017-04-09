package com.art.alligator.defaultimpementation;

import com.art.alligator.AnimationData;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionAnimationProvider;
import com.art.alligator.TransitionType;

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
