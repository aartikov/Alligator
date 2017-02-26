package com.art.alligator.implementation;

import com.art.alligator.AnimationProvider;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionAnimationDirection;

/**
 * Date: 24.02.2017
 * Time: 19:14
 *
 * @author Artur Artikov
 */
public class DefaultAnimationProvider implements AnimationProvider {
	@Override
	public TransitionAnimation getAnimation(TransitionAnimationDirection direction, boolean isActivity, Class<? extends Screen> screenClass) {
		return TransitionAnimation.DEFAULT;
	}
}
