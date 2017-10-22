package me.aartikov.alligator.screenswitchers;

import java.util.HashMap;
import java.util.Map;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.navigationfactories.NavigationFactorySetter;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.screenimplementations.ScreenImplementation;
import me.aartikov.alligator.ScreenResolver;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.exceptions.ScreenSwitchingException;
import me.aartikov.alligator.helpers.FragmentSwitcher;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;

/**
 * Date: 01/30/2016
 * Time: 10:13
 *
 * @author Artur Artikov
 */

/**
 * Screen switcher that switches fragments in a container. It uses {@link NavigationFactory} to create fragments and get screens back from it.
 * Screens used by {@code FragmentScreenSwitcher} must have {@code equals} and {@code hashCode} correctly overridden.
 */
public class FragmentScreenSwitcher implements ScreenSwitcher, NavigationFactorySetter {
	public interface AnimationProvider {
		TransitionAnimation getAnimation(Screen screenFrom, Screen screenTo, AnimationData animationData);
	}

	private FragmentSwitcher fragmentSwitcher;
	private NavigationFactory mNavigationFactory;
	private ScreenResolver mScreenResolver;
	private AnimationProvider mAnimationProvider;
	private Map<Screen, Fragment> mFragmentMap;

	/**
	 * @param fragmentManager   fragment manager used for fragment transactions
	 * @param containerId       id of a container where fragments will be added
	 * @param animationProvider animation provider
	 */
	public FragmentScreenSwitcher(FragmentManager fragmentManager, int containerId, AnimationProvider animationProvider) {
		fragmentSwitcher = new FragmentSwitcher(fragmentManager, containerId);
		mAnimationProvider = animationProvider;
	}

	/**
	 * @param fragmentManager fragment manager used for fragment transactions
	 * @param containerId     id of a container where fragments will be added
	 */
	public FragmentScreenSwitcher(FragmentManager fragmentManager, int containerId) {
		this(fragmentManager, containerId, createDefaultAnimationProvider());
	}

	@Override
	public void setNavigationFactory(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
		mScreenResolver = new ScreenResolver(mNavigationFactory);
		if (mFragmentMap == null) {
			initFragmentMap();
		}
	}

	@Override
	public void switchTo(Screen screen, @Nullable AnimationData animationData) throws ScreenSwitchingException {
		ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screen.getClass());
		if (screenImplementation instanceof FragmentScreenImplementation) {
			Screen currentScreen = getCurrentScreen();
			Fragment fragment = getOrCreateFragment(screen, (FragmentScreenImplementation)screenImplementation);
			TransitionAnimation animation = currentScreen != null ? mAnimationProvider.getAnimation(currentScreen, screen, animationData) : TransitionAnimation.DEFAULT;
			fragmentSwitcher.switchTo(fragment, animation);
		} else {
			throw new ScreenSwitchingException("Screen " + screen.getClass().getSimpleName() + " is not represented by a fragment.");
		}
	}

	@Override
	public
	@Nullable
	Screen getCurrentScreen() {
		Fragment currentFragment = getCurrentFragment();
		return currentFragment != null ? getScreen(currentFragment) : null;
	}

	/**
	 * Returns a current fragment.
	 *
	 * @return current fragment in the container, or {@code null} if there are no fragments in the container
	 */
	public Fragment getCurrentFragment() {
		return fragmentSwitcher.getCurrentFragment();
	}

	private static AnimationProvider createDefaultAnimationProvider() {
		return new AnimationProvider() {
			@Override
			public TransitionAnimation getAnimation(Screen screenFrom, Screen screenTo, AnimationData animationData) {
				return TransitionAnimation.DEFAULT;
			}
		};
	}

	private void initFragmentMap() {
		mFragmentMap = new HashMap<>();
		for (Fragment fragment : fragmentSwitcher.getFragments()) {
			mFragmentMap.put(mScreenResolver.getScreen(fragment), fragment);
		}
	}

	private Screen getScreen(Fragment fragment) {
		for (Map.Entry<Screen, Fragment> entry : mFragmentMap.entrySet()) {
			if (entry.getValue() == fragment) {
				return entry.getKey();
			}
		}
		return null;
	}

	private Fragment getOrCreateFragment(Screen screen, FragmentScreenImplementation screenImplementation) throws ScreenSwitchingException {
		Fragment fragment = mFragmentMap.get(screen);
		if (fragment == null) {
			fragment = screenImplementation.createFragment(screen);
			try {
				mScreenResolver.getScreen(fragment);  // Check that the screen has a valid screen getting function
			} catch (Exception e) {
				throw new ScreenSwitchingException(e.getMessage());
			}
			mFragmentMap.put(screen, fragment);
		}
		return fragment;
	}
}
