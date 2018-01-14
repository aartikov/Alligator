package me.aartikov.advancedscreenswitchersample;

import android.support.annotation.Nullable;

import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.SimpleTransitionAnimation;

/**
 * Date: 28.02.2017
 * Time: 21:01
 *
 * @author Artur Artikov
 */
public class SampleTransitionAnimationProvider implements TransitionAnimationProvider {
	@Override
	public TransitionAnimation getAnimation(TransitionType transitionType, Class<? extends Screen> screenClassFrom, Class<? extends Screen> screenClassTo, boolean isActivity, @Nullable AnimationData animationData) {
		if (isActivity) {
			return TransitionAnimation.DEFAULT;
		} else {
			return new SimpleTransitionAnimation(R.anim.stay, R.anim.fade_out);
		}
	}
}