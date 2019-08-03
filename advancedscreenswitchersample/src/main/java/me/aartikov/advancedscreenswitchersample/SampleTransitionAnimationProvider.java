package me.aartikov.advancedscreenswitchersample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.SimpleTransitionAnimation;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider;

/**
 * Date: 28.02.2017
 * Time: 21:01
 *
 * @author Artur Artikov
 */
public class SampleTransitionAnimationProvider implements TransitionAnimationProvider {
	@Override
	@NonNull
	public TransitionAnimation getAnimation(@NonNull TransitionType transitionType, @NonNull DestinationType destinationType,
											@NonNull Class<? extends Screen> screenClassFrom, @NonNull Class<? extends Screen> screenClassTo,
											@Nullable AnimationData animationData) {
		if (destinationType == DestinationType.ACTIVITY) {
			return TransitionAnimation.DEFAULT;
		} else {
			return new SimpleTransitionAnimation(R.anim.stay, R.anim.fade_out);
		}
	}
}