package me.aartikov.alligator.navigators;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.exceptions.ActivityResolvingException;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.ScreenRegistrationException;
import me.aartikov.alligator.helpers.ActivityHelper;
import me.aartikov.alligator.helpers.ScreenResultHelper;
import me.aartikov.alligator.listeners.TransitionListener;
import me.aartikov.alligator.navigationfactories.NavigationFactory;

public class DefaultActivityNavigator implements ActivityNavigator {
	@NonNull
	private AppCompatActivity mActivity;

	@NonNull
	private NavigationFactory mNavigationFactory;

	@NonNull
	private ActivityHelper mActivityHelper;

	@NonNull
	private ScreenResultHelper mScreenResultHelper;

	@NonNull
	private TransitionListener mTransitionListener;

	@NonNull
	private TransitionAnimationProvider mAnimationProvider;

	public DefaultActivityNavigator(@NonNull AppCompatActivity activity,
									@NonNull NavigationFactory navigationFactory,
									@NonNull TransitionListener transitionListener,
									@NonNull TransitionAnimationProvider animationProvider) {
		mActivity = activity;
		mNavigationFactory = navigationFactory;
		mActivityHelper = new ActivityHelper(activity);
		mScreenResultHelper = new ScreenResultHelper();
		mTransitionListener = transitionListener;
		mAnimationProvider = animationProvider;
	}

	@Override
	public void goForward(@NonNull Screen screen,
						  @NonNull ActivityDestination destination,
						  @Nullable AnimationData animationData) throws NavigationException {

		Class<? extends Screen> screenClassFrom = mNavigationFactory.getScreenClass(mActivity);
		Class<? extends Screen> screenClassTo = screen.getClass();

		Intent intent = destination.createIntent(mActivity, screen, screenClassFrom);
		if (!mActivityHelper.resolve(intent)) {
			throw new ActivityResolvingException(screen);
		}

		TransitionAnimation animation = getAnimation(TransitionType.FORWARD, screenClassFrom, screenClassTo, animationData);

		if (destination.getScreenResultClass() != null) {
			mActivityHelper.startForResult(intent, destination.getRequestCode(), animation);
		} else {
			mActivityHelper.start(intent, animation);
		}

		callTransitionListener(TransitionType.FORWARD, screenClassFrom, screenClassTo);
	}


	@Override
	public void replace(@NonNull Screen screen,
						@NonNull ActivityDestination destination,
						@Nullable AnimationData animationData) throws NavigationException {

		Class<? extends Screen> previousScreenClass = mNavigationFactory.getPreviousScreenClass(mActivity);
		Intent intent = destination.createIntent(mActivity, screen, previousScreenClass);

		if (!mActivityHelper.resolve(intent)) {
			throw new ActivityResolvingException(screen);
		}

		Class<? extends Screen> screenClassFrom = mNavigationFactory.getScreenClass(mActivity);
		Class<? extends Screen> screenClassTo = screen.getClass();
		TransitionAnimation animation = getAnimation(TransitionType.REPLACE, screenClassFrom, screenClassTo, animationData);

		mActivityHelper.start(intent, animation);
		mActivityHelper.finish(animation);
		callTransitionListener(TransitionType.REPLACE, screenClassFrom, screenClassTo);
	}

	@Override
	public void reset(@NonNull Screen screen,
					  @NonNull ActivityDestination destination,
					  @Nullable AnimationData animationData) throws NavigationException {

		Intent intent = destination.createIntent(mActivity, screen, null);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

		if (!mActivityHelper.resolve(intent)) {
			throw new ActivityResolvingException(screen);
		}

		Class<? extends Screen> screenClassFrom = mNavigationFactory.getScreenClass(mActivity);
		Class<? extends Screen> screenClassTo = screen.getClass();
		TransitionAnimation animation = getAnimation(TransitionType.RESET, screenClassFrom, screenClassTo, animationData);

		mActivityHelper.start(intent, animation);
		callTransitionListener(TransitionType.RESET, screenClassFrom, screenClassTo);
	}

	@Override
	public void goBack(@Nullable ScreenResult screenResult,
					   @Nullable AnimationData animationData) throws NavigationException {

		if (screenResult != null) {
			mScreenResultHelper.setActivityResult(mActivity, screenResult, mNavigationFactory);
		}

		Class<? extends Screen> screenClassFrom = mNavigationFactory.getScreenClass(mActivity);
		Class<? extends Screen> screenClassTo = mNavigationFactory.getPreviousScreenClass(mActivity);
		TransitionAnimation animation = getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, animationData);

		mActivityHelper.finish(animation);
		callTransitionListener(TransitionType.BACK, screenClassFrom, screenClassTo);
	}

	@Override
	public void goBackTo(@NonNull Class<? extends Screen> screenClass,
						 @NonNull ActivityDestination destination,
						 @Nullable ScreenResult screenResult,
						 @Nullable AnimationData animationData) throws NavigationException {

		Intent intent = destination.createEmptyIntent(mActivity, screenClass);
		if (intent == null) {
			throw new ScreenRegistrationException("Can't create intent for a screen " + screenClass.getSimpleName());
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		if (screenResult != null) {
			mScreenResultHelper.setResultToIntent(intent, mActivity, screenResult, mNavigationFactory);
		}

		Class<? extends Screen> screenClassFrom = mNavigationFactory.getScreenClass(mActivity);
		TransitionAnimation animation = getAnimation(TransitionType.BACK, screenClassFrom, screenClass, animationData);

		mActivityHelper.start(intent, animation);
		callTransitionListener(TransitionType.BACK, screenClassFrom, screenClass);
	}

	private TransitionAnimation getAnimation(@NonNull TransitionType transitionType,
											 @Nullable Class<? extends Screen> screenClassFrom,
											 @Nullable Class<? extends Screen> screenClassTo,
											 @Nullable AnimationData animationData) {

		if (screenClassFrom == null || screenClassTo == null) {
			return TransitionAnimation.DEFAULT;
		} else {
			return mAnimationProvider.getAnimation(transitionType, DestinationType.ACTIVITY, screenClassFrom, screenClassTo, animationData);
		}
	}

	private void callTransitionListener(@NonNull TransitionType transitionType,
										@Nullable Class<? extends Screen> screenClassFrom,
										@Nullable Class<? extends Screen> screenClassTo) {
		mTransitionListener.onScreenTransition(transitionType, DestinationType.ACTIVITY, screenClassFrom, screenClassTo);
	}
}
