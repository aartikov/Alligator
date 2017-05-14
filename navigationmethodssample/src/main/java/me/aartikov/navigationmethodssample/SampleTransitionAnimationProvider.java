package me.aartikov.navigationmethodssample;

import android.support.annotation.Nullable;

import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionAnimation;
import me.aartikov.alligator.TransitionAnimationProvider;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.transition.SimpleTransitionAnimation;

/**
 * Date: 25.02.2017
 * Time: 12:00
 *
 * @author Artur Artikov
 */

public class SampleTransitionAnimationProvider implements TransitionAnimationProvider {
	@Override
	public TransitionAnimation getAnimation(TransitionType transitionType, Class<? extends Screen> screenClassFrom, Class<? extends Screen> screenClassTo, boolean isActivity, @Nullable AnimationData animationData) {
		switch (transitionType) {
			case FORWARD:
				return new SimpleTransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left);
			case BACK:
				return new SimpleTransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right);
			case REPLACE:
			case RESET:
				return new SimpleTransitionAnimation(R.anim.stay, R.anim.fade_out);
			default:
				return TransitionAnimation.DEFAULT;
		}
	}
}
