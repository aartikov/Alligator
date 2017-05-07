package me.aartikov.sharedelementanimation;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;

import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionAnimation;
import me.aartikov.alligator.TransitionAnimationProvider;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.transition.LollipopTransitionAnimation;
import me.aartikov.alligator.animations.transition.SimpleTransitionAnimation;
import me.aartikov.sharedelementanimation.ui.SharedElementProvider;

/**
 * Date: 08.05.2017
 * Time: 01:47
 *
 * @author Artur Artikov
 */

public class SampleTransitionAnimationProvider implements TransitionAnimationProvider {
	private AppCompatActivity mActivity;

	public SampleTransitionAnimationProvider(AppCompatActivity activity) {
		mActivity = activity;
	}

	@Override
	public TransitionAnimation getAnimation(TransitionType transitionType, Class<? extends Screen> screenClassFrom, Class<? extends Screen> screenClassTo, boolean isActivity, @Nullable AnimationData animationData) {
		if (transitionType == TransitionType.FORWARD) {
			return createSlideAnimation(true, animationData);
		} else if (transitionType == TransitionType.BACK) {
			return createSlideAnimation(false, animationData);
		} else {
			return TransitionAnimation.DEFAULT;
		}
	}

	@SuppressLint("RtlHardcoded")
	private TransitionAnimation createSlideAnimation(boolean forward, AnimationData animationData) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Transition enterTransition = forward ? new Slide(Gravity.RIGHT) : new Slide(Gravity.LEFT);
			Transition exitTransition = forward ? new Slide(Gravity.LEFT) : new Slide(Gravity.RIGHT);
			LollipopTransitionAnimation animation = new LollipopTransitionAnimation(enterTransition, exitTransition);
			animation.setAllowEnterTransitionOverlap(false);

			Fragment currentFragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.main_container);
			if (currentFragment instanceof SharedElementProvider) {
				SharedElementProvider sharedElementProvider = (SharedElementProvider) currentFragment;
				View sharedElement = sharedElementProvider.getSharedElement(animationData);
				String shareElementName = sharedElementProvider.getSharedElementName(animationData);
				animation.addSharedElement(sharedElement, shareElementName);
				Transition moveTransition = TransitionInflater.from(mActivity).inflateTransition(android.R.transition.move);
				moveTransition.setDuration(600);
				animation.setSharedElementTransition(moveTransition);
			}
			return animation;
		} else {
			int enterAnimRes = forward ? R.anim.slide_in_right : R.anim.slide_in_left;
			int exitAnimRes = forward ? R.anim.slide_out_left : R.anim.slide_out_right;
			return new SimpleTransitionAnimation(enterAnimRes, exitAnimRes);
		}
	}
}
