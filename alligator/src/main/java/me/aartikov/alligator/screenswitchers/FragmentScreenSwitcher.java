package me.aartikov.alligator.screenswitchers;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResolver;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.ScreenRegistrationException;
import me.aartikov.alligator.helpers.FragmentSwitcher;
import me.aartikov.alligator.listeners.ScreenSwitchingListener;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.ScreenImplementation;

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
public class FragmentScreenSwitcher implements ScreenSwitcher {
	public interface AnimationProvider {
		TransitionAnimation getAnimation(Screen screenFrom, Screen screenTo, AnimationData animationData);
	}

	private FragmentSwitcher fragmentSwitcher;
	private NavigationFactory mNavigationFactory;
	private ScreenResolver mScreenResolver;
	private AnimationProvider mAnimationProvider;
	private Map<Screen, Fragment> mFragmentMap;

	/**
	 * @param navigationFactory navigation factory used to create fragments
	 * @param fragmentManager   fragment manager used for fragment transactions
	 * @param containerId       id of a container where fragments will be added
	 * @param animationProvider animation provider
	 */
	public FragmentScreenSwitcher(NavigationFactory navigationFactory, FragmentManager fragmentManager, int containerId, AnimationProvider animationProvider) {
		mNavigationFactory = navigationFactory;
		mScreenResolver = new ScreenResolver(mNavigationFactory);
		fragmentSwitcher = new FragmentSwitcher(fragmentManager, containerId);
		mAnimationProvider = animationProvider;
		initFragmentMap();
	}

	/**
	 * @param navigationFactory navigation factory used to create fragments
	 * @param fragmentManager   fragment manager used for fragment transactions
	 * @param containerId       id of a container where fragments will be added
	 */
	public FragmentScreenSwitcher(NavigationFactory navigationFactory, FragmentManager fragmentManager, int containerId) {
		this(navigationFactory, fragmentManager, containerId, createDefaultAnimationProvider());
	}

	@Override
	public void switchTo(Screen screen, ScreenSwitchingListener listener, @Nullable AnimationData animationData) throws NavigationException {
		Screen currentScreen = getCurrentScreen();
		if (currentScreen != null && currentScreen.equals(screen)) {
			return;
		}

		ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screen.getClass());
		if (screenImplementation instanceof FragmentScreenImplementation) {
			Fragment fragment = getOrCreateFragment(screen, (FragmentScreenImplementation) screenImplementation);
			TransitionAnimation animation = currentScreen != null ? mAnimationProvider.getAnimation(currentScreen, screen, animationData) : TransitionAnimation.DEFAULT;
			fragmentSwitcher.switchTo(fragment, animation);
			listener.onScreenSwitched(currentScreen, screen);
		} else {
			throw new ScreenRegistrationException("Screen " + screen.getClass().getSimpleName() + " is not represented by a fragment.");
		}
	}

	/**
	 * Returns a current fragment.
	 *
	 * @return current fragment in the container, or {@code null} if there are no fragments in the container
	 */
	@Nullable
	public Fragment getCurrentFragment() {
		return fragmentSwitcher.getCurrentFragment();
	}

	@Nullable
	private Screen getCurrentScreen() {
		Fragment currentFragment = getCurrentFragment();
		return currentFragment != null ? getScreen(currentFragment) : null;
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

	private Fragment getOrCreateFragment(Screen screen, FragmentScreenImplementation screenImplementation) throws NavigationException {
		Fragment fragment = mFragmentMap.get(screen);
		if (fragment == null) {
			fragment = screenImplementation.createFragment(screen);
			try {
				mScreenResolver.getScreen(fragment);  // Check that the screen has a valid screen getting function
			} catch (Exception e) {
				throw new ScreenRegistrationException(e.getMessage());
			}
			mFragmentMap.put(screen, fragment);
		}
		return fragment;
	}
}
