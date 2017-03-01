package com.art.screenswitchersample;

import com.art.alligator.AnimationProvider;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionType;

/**
 * Date: 28.02.2017
 * Time: 21:01
 *
 * @author Artur Artikov
 */

public class SampleAnimationProvider implements AnimationProvider {
	@Override
	public TransitionAnimation getAnimation(TransitionType transitionType, boolean isActivity, Class<? extends Screen> screenClassFrom, Class<? extends Screen> screenClassTo) {
		if(isActivity) {
			return TransitionAnimation.DEFAULT;
		} else {
			return new TransitionAnimation(R.anim.stay, R.anim.fade_out);
		}
	}
}
