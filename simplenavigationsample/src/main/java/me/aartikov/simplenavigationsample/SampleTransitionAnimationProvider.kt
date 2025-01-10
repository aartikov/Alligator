package me.aartikov.simplenavigationsample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.SimpleTransitionAnimation;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider;


// Lets make a nice slide animation.
public class SampleTransitionAnimationProvider implements TransitionAnimationProvider {
	@Override
	@NonNull
	public TransitionAnimation getAnimation(@NonNull TransitionType transitionType,
											@NonNull DestinationType destinationType,
											@NonNull Class<? extends Screen> screenClassFrom,
											@NonNull Class<? extends Screen> screenClassTo,
											@Nullable AnimationData animationData) {
		switch (transitionType) {
			case FORWARD:
				return new SimpleTransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left);
			case BACK:
				return new SimpleTransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right);
			default:
				return TransitionAnimation.DEFAULT;
		}
	}
}
