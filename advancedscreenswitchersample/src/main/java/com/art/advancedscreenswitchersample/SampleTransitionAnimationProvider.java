package com.art.advancedscreenswitchersample;

import com.art.alligator.AnimationData;
import com.art.alligator.TransitionAnimationProvider;
import com.art.alligator.Screen;
import com.art.alligator.animations.TransitionAnimation;
import com.art.alligator.TransitionType;

/**
 * Date: 28.02.2017
 * Time: 21:01
 *
 * @author Artur Artikov
 */

public class SampleTransitionAnimationProvider implements TransitionAnimationProvider {
	@Override
	public TransitionAnimation getAnimation(TransitionType transitionType, Class<? extends Screen> screenClassFrom, Class<? extends Screen> screenClassTo, boolean isActivity, AnimationData animationData) {
		if(isActivity) {
			return TransitionAnimation.DEFAULT;
		} else {
			return new TransitionAnimation(R.anim.stay, R.anim.fade_out);
		}
	}
}
