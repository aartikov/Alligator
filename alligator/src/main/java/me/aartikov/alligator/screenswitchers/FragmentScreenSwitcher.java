package me.aartikov.alligator.screenswitchers;

import java.util.HashMap;
import java.util.Map;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.NavigationFactory;
import me.aartikov.alligator.NavigationFactorySetter;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenSwitcher;
import me.aartikov.alligator.TransitionAnimation;
import me.aartikov.alligator.ViewType;
import me.aartikov.alligator.exceptions.ScreenSwitchingException;
import me.aartikov.alligator.internal.FragmentSwitcher;
import me.aartikov.alligator.internal.ScreenClassUtils;

/**
 * Date: 01/30/2016
 * Time: 10:13
 *
 * @author Artur Artikov
 */

/**
 * Screen switcher that switches fragments in a container.
 */
public class FragmentScreenSwitcher implements ScreenSwitcher, NavigationFactorySetter {
	public interface AnimationProvider {
		TransitionAnimation getAnimation(Screen screenFrom, Screen screenTo, AnimationData animationData);
	}

	private FragmentSwitcher fragmentSwitcher;
	private NavigationFactory mNavigationFactory;
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
		if (mFragmentMap == null) {
			initFragmentMap();
		}
	}

	@Override
	public void switchTo(Screen screen, @Nullable AnimationData animationData) throws ScreenSwitchingException {
		if (mNavigationFactory.getViewType(screen.getClass()) != ViewType.FRAGMENT) {
			throw new ScreenSwitchingException("Screen " + screen.getClass().getSimpleName() + " is not represented by a fragment.");
		}

		Screen currentScreen = getCurrentScreen();
		Fragment fragment = getOrCreateFragment(screen);
		TransitionAnimation animation = currentScreen != null ? mAnimationProvider.getAnimation(currentScreen, screen, animationData) : TransitionAnimation.DEFAULT;
		fragmentSwitcher.switchTo(fragment, animation);
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
			Class<? extends Screen> screenClass = ScreenClassUtils.getScreenClass(fragment);
			Screen screen = screenClass != null ? mNavigationFactory.getScreen(fragment, screenClass) : null;
			if (screen != null) {
				mFragmentMap.put(screen, fragment);
			}
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

	private Fragment getOrCreateFragment(Screen screen) throws ScreenSwitchingException {
		Fragment fragment = mFragmentMap.get(screen);
		if (fragment == null) {
			fragment = mNavigationFactory.createFragment(screen);
			try {
				mNavigationFactory.getScreen(fragment, screen.getClass());  // Check that the screen has a valid screen getting function
			} catch (Exception e) {
				throw new ScreenSwitchingException(e.getMessage());
			}

			ScreenClassUtils.putScreenClass(fragment, screen.getClass());
			mFragmentMap.put(screen, fragment);
		}
		return fragment;
	}
}
