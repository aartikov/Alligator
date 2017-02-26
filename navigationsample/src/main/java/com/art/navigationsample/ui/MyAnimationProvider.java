package com.art.navigationsample.ui;

import android.app.Activity;

import com.art.alligator.TransitionAnimation;
import com.art.alligator.AnimationProvider;
import com.art.alligator.Screen;
import com.art.navigationsample.R;

/**
 * Date: 25.02.2017
 * Time: 12:00
 *
 * @author Artur Artikov
 */

public class MyAnimationProvider implements AnimationProvider {
	@Override
	public TransitionAnimation getActivityForwardAnimation(Class<? extends Screen> screenClass) {
		return new TransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	@Override
	public TransitionAnimation getActivityBackAnimation(Class<? extends Activity> activityClass) {
		return new TransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public TransitionAnimation getActivityReplaceAnimation(Class<? extends Screen> screenClass) {
		return new TransitionAnimation(android.R.anim.fade_in, R.anim.stay);
	}

	@Override
	public TransitionAnimation getFragmentForwardAnimation(Class<? extends Screen> screenClass) {
		return new TransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	@Override
	public TransitionAnimation getFragmentBackAnimation(Class<? extends Screen> screenClass) {
		return new TransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public TransitionAnimation getFragmentReplaceAnimation(Class<? extends Screen> screenClass) {
		return new TransitionAnimation(android.R.anim.fade_in, R.anim.stay);
	}
}
