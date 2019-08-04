package me.aartikov.alligator.navigators;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.ScreenNotFoundException;
import me.aartikov.alligator.exceptions.ScreenRegistrationException;
import me.aartikov.alligator.helpers.FragmentStack;
import me.aartikov.alligator.helpers.ScreenResultHelper;
import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.alligator.listeners.TransitionListener;
import me.aartikov.alligator.navigationfactories.NavigationFactory;

public class DefaultFragmentNavigator implements FragmentNavigator {
	@NonNull
	private FragmentStack mFragmentStack;

	@NonNull
	private NavigationFactory mNavigationFactory;

	@NonNull
	private ScreenResultHelper mScreenResultHelper;

	@NonNull
	private TransitionListener mTransitionListener;

	@NonNull
	private ScreenResultListener mScreenResultListener;

	@NonNull
	private TransitionAnimationProvider mAnimationProvider;

	public DefaultFragmentNavigator(@NonNull FragmentManager fragmentManager,
									@IdRes int containerId,
									@NonNull NavigationFactory navigationFactory,
									@NonNull TransitionListener transitionListener,
									@NonNull ScreenResultListener screenResultListener,
									@NonNull TransitionAnimationProvider animationProvider) {
		mFragmentStack = new FragmentStack(fragmentManager, containerId);
		mNavigationFactory = navigationFactory;
		mScreenResultHelper = new ScreenResultHelper();
		mTransitionListener = transitionListener;
		mScreenResultListener = screenResultListener;
		mAnimationProvider = animationProvider;
	}

	@Override
	public void goForward(@NonNull Screen screen,
						  @NonNull FragmentDestination destination,
						  @Nullable AnimationData animationData) throws NavigationException {

		Fragment currentFragment = mFragmentStack.getCurrentFragment();
		Class<? extends Screen> screenClassFrom = currentFragment == null ? null : mNavigationFactory.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = screen.getClass();

		Fragment fragment = destination.createFragment(screen);
		if (fragment instanceof DialogFragment) {
			throw new ScreenRegistrationException("DialogFragment is used as usual Fragment.");
		}
		TransitionAnimation animation = getAnimation(TransitionType.FORWARD, screenClassFrom, screenClassTo, animationData);
		mFragmentStack.push(fragment, animation);
		callTransitionListener(TransitionType.FORWARD, screenClassFrom, screenClassTo);
	}

	@Override
	public void replace(@NonNull Screen screen,
						@NonNull FragmentDestination destination,
						@Nullable AnimationData animationData) throws NavigationException {

		Fragment fragment = destination.createFragment(screen);
		Fragment currentFragment = mFragmentStack.getCurrentFragment();

		Class<? extends Screen> screenClassFrom = currentFragment == null ? null : mNavigationFactory.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = screen.getClass();
		TransitionAnimation animation = getAnimation(TransitionType.REPLACE, screenClassFrom, screenClassTo, animationData);

		mFragmentStack.replace(fragment, animation);
		callTransitionListener(TransitionType.REPLACE, screenClassFrom, screenClassTo);
	}

	@Override
	public void reset(@NonNull Screen screen,
					  @NonNull FragmentDestination destination,
					  @Nullable AnimationData animationData) throws NavigationException {

		Fragment fragment = destination.createFragment(screen);
		Fragment currentFragment = mFragmentStack.getCurrentFragment();

		Class<? extends Screen> screenClassFrom = currentFragment == null ? null : mNavigationFactory.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = screen.getClass();
		TransitionAnimation animation = getAnimation(TransitionType.RESET, screenClassFrom, screenClassTo, animationData);

		mFragmentStack.reset(fragment, animation);
		callTransitionListener(TransitionType.RESET, screenClassFrom, screenClassTo);
	}

	@Override
	public boolean canGoBack() {
		return mFragmentStack.getFragmentCount() > 1;
	}

	@Override
	public void goBack(@Nullable ScreenResult screenResult,
					   @Nullable AnimationData animationData) throws NavigationException {

		List<Fragment> fragments = mFragmentStack.getFragments();
		Fragment currentFragment = fragments.get(fragments.size() - 1);
		Fragment previousFragment = fragments.get(fragments.size() - 2);

		Class<? extends Screen> screenClassFrom = mNavigationFactory.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = mNavigationFactory.getScreenClass(previousFragment);

		TransitionAnimation animation = getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, animationData);
		mFragmentStack.pop(animation);
		callTransitionListener(TransitionType.BACK, screenClassFrom, screenClassTo);
		mScreenResultHelper.callScreenResultListener(currentFragment, screenResult, mScreenResultListener, mNavigationFactory);
	}

	@Override
	public void goBackTo(@NonNull Class<? extends Screen> screenClass,
						 @NonNull FragmentDestination destination,
						 @Nullable ScreenResult screenResult,
						 @Nullable AnimationData animationData) throws NavigationException {

		List<Fragment> fragments = mFragmentStack.getFragments();
		Fragment requiredFragment = null;
		boolean toPrevious = false;
		for (int i = fragments.size() - 1; i >= 0; i--) {
			if (screenClass == mNavigationFactory.getScreenClass(fragments.get(i))) {
				requiredFragment = fragments.get(i);
				toPrevious = i == fragments.size() - 2;
				break;
			}
		}

		if (requiredFragment == null) {
			throw new ScreenNotFoundException(screenClass);
		}

		Fragment currentFragment = fragments.get(fragments.size() - 1);
		Class<? extends Screen> screenClassFrom = mNavigationFactory.getScreenClass(currentFragment);
		TransitionAnimation animation = getAnimation(TransitionType.BACK, screenClassFrom, screenClass, animationData);

		mFragmentStack.popUntil(requiredFragment, animation);
		callTransitionListener(TransitionType.BACK, screenClassFrom, screenClass);
		if (screenResult != null || toPrevious) {
			mScreenResultHelper.callScreenResultListener(currentFragment, screenResult, mScreenResultListener, mNavigationFactory);
		}
	}

	private TransitionAnimation getAnimation(@NonNull TransitionType transitionType,
											 @Nullable Class<? extends Screen> screenClassFrom,
											 @Nullable Class<? extends Screen> screenClassTo,
											 @Nullable AnimationData animationData) {

		if (screenClassFrom == null || screenClassTo == null) {
			return TransitionAnimation.DEFAULT;
		} else {
			return mAnimationProvider.getAnimation(transitionType, DestinationType.FRAGMENT, screenClassFrom, screenClassTo, animationData);
		}
	}

	private void callTransitionListener(@NonNull TransitionType transitionType,
										@Nullable Class<? extends Screen> screenClassFrom,
										@Nullable Class<? extends Screen> screenClassTo) {

		mTransitionListener.onScreenTransition(transitionType, DestinationType.FRAGMENT, screenClassFrom, screenClassTo);
	}
}
