package me.aartikov.alligator.animations.providers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.TransitionAnimation;

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
	@NonNull
	public TransitionAnimation getAnimation(@NonNull TransitionType transitionType, @NonNull Class<? extends Screen> screenClassFrom, @NonNull Class<? extends Screen> screenClassTo,
	                                        boolean isActivity, @Nullable AnimationData animationData) {
		return TransitionAnimation.DEFAULT;
	}
}
