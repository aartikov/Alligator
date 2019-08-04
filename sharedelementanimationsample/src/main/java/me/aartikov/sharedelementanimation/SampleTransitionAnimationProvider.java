package me.aartikov.sharedelementanimation;

import android.annotation.SuppressLint;
import android.os.Build;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.LollipopTransitionAnimation;
import me.aartikov.alligator.animations.SimpleTransitionAnimation;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider;
import me.aartikov.sharedelementanimation.ui.SharedElementProvider;


public class SampleTransitionAnimationProvider implements TransitionAnimationProvider {
	private AppCompatActivity mActivity;

	public SampleTransitionAnimationProvider(AppCompatActivity activity) {
		mActivity = activity;
	}

	@Override
	@NonNull
	public TransitionAnimation getAnimation(@NonNull TransitionType transitionType,
											@NonNull DestinationType destinationType,
											@NonNull Class<? extends Screen> screenClassFrom,
											@NonNull Class<? extends Screen> screenClassTo,
											@Nullable AnimationData animationData) {

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

			Fragment currentFragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
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
