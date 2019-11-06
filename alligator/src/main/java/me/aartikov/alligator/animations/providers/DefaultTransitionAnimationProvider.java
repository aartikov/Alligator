package me.aartikov.alligator.animations.providers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.TransitionAnimation;


/**
 * Default implementation of {@link TransitionAnimationProvider}. Always returns {@code TransitionAnimation.DEFAULT}.
 */
public class DefaultTransitionAnimationProvider implements TransitionAnimationProvider {

	@Override
	@NonNull
	public TransitionAnimation getAnimation(@NonNull TransitionType transitionType,
											@NonNull DestinationType destinationType,
											@NonNull Class<? extends Screen> screenClassFrom,
											@NonNull Class<? extends Screen> screenClassTo,
											@Nullable AnimationData animationData) {
		return TransitionAnimation.DEFAULT;
	}
}
