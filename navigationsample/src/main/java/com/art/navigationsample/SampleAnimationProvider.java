package com.art.navigationsample;

import com.art.alligator.AnimationProvider;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionType;
import com.art.alligator.AnimationData;

/**
 * Date: 25.02.2017
 * Time: 12:00
 *
 * @author Artur Artikov
 */

public class SampleAnimationProvider implements AnimationProvider {
	@Override
	public TransitionAnimation getAnimation(TransitionType transitionType, Class<? extends Screen> screenClassFrom, Class<? extends Screen> screenClassTo, boolean isActivity, AnimationData animationData) {
		switch (transitionType) {
			case FORWARD:
				return new TransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left);
			case BACK:
				return new TransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right);
			case REPLACE:
			case RESET:
				return new TransitionAnimation(R.anim.stay, R.anim.fade_out);
			default:
				return TransitionAnimation.DEFAULT;
		}
	}
}
