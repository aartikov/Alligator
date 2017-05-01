package me.aartikov.sharedelementanimation.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;

import butterknife.ButterKnife;
import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.TransitionAnimation;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.transition.LollipopTransitionAnimation;
import me.aartikov.alligator.animations.transition.SimpleTransitionAnimation;
import me.aartikov.sharedelementanimation.R;
import me.aartikov.sharedelementanimation.SampleApplication;
import me.aartikov.sharedelementanimation.screens.FirstScreen;

public class MainActivity extends AppCompatActivity {
	private Navigator mNavigator;
	private NavigationContextBinder mNavigationContextBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		mNavigator = SampleApplication.getNavigator();
		mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

		if (savedInstanceState == null) {
			mNavigator.reset(new FirstScreen());
		}
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();

		NavigationContext navigationContext = new NavigationContext.Builder(this)
				.containerId(R.id.main_container)
				.transitionAnimationProvider((transitionType, screenClassFrom, screenClassTo, isActivity, animationData) -> {
					if (transitionType == TransitionType.FORWARD) {
						return createSlideAnimation(true, animationData);
					} else if (transitionType == TransitionType.BACK) {
						return createSlideAnimation(false, animationData);
					} else {
						return TransitionAnimation.DEFAULT;
					}
				})
				.build();
		mNavigationContextBinder.bind(navigationContext);
	}

	@Override
	protected void onPause() {
		mNavigationContextBinder.unbind();
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
	}

	@SuppressLint("RtlHardcoded")
	private TransitionAnimation createSlideAnimation(boolean forward, AnimationData animationData) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Transition enterTransition = forward ? new Slide(Gravity.RIGHT) : new Slide(Gravity.LEFT);
			Transition exitTransition = forward ? new Slide(Gravity.LEFT) : new Slide(Gravity.RIGHT);
			LollipopTransitionAnimation animation = new LollipopTransitionAnimation(enterTransition, exitTransition);
			animation.setAllowEnterTransitionOverlap(false);

			Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
			if (currentFragment instanceof SharedElementProvider) {
				SharedElementProvider sharedElementProvider = (SharedElementProvider) currentFragment;
				View sharedElement = sharedElementProvider.getSharedElement(animationData);
				String shareElementName = sharedElementProvider.getSharedElementName(animationData);
				animation.addSharedElement(sharedElement, shareElementName);
				Transition moveTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move);
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
