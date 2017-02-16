package com.art.alligator.implementation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.ScreenSwitcher;

/**
 * Date: 01/30/2016
 * Time: 10:13
 *
 * @author Artur Artikov
 */
public abstract class FragmentScreenSwitcher implements ScreenSwitcher {
	private FragmentManager mFragmentManager;
	private int mContainerId;
	private NavigationFactory mNavigationFactory;
	private Fragment mCurrentFragment;

	public FragmentScreenSwitcher(FragmentManager fragmentManager, int containerId, NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
		mFragmentManager = fragmentManager;
		mContainerId = containerId;
		mCurrentFragment = mFragmentManager.findFragmentById(mContainerId);
	}

	public abstract Screen createScreen(String screenName);

	protected void onScreenSwitched(String screenName) {
	}

	@Override
	public boolean switchTo(String screenName) {
		Fragment newFragment = mFragmentManager.findFragmentByTag(screenName);
		if (newFragment == null) {
			newFragment = createFragment(screenName, mNavigationFactory);
			if(newFragment == null) {
				return false;
			}

			mFragmentManager.beginTransaction()
					.add(mContainerId, newFragment, screenName)
					.detach(newFragment)
					.commitNow();
		}

		if(mCurrentFragment == newFragment) {
			return true;
		}

		if(mCurrentFragment != null) {
			mFragmentManager.beginTransaction()
					.detach(mCurrentFragment)
					.commitNow();
		}

		mCurrentFragment = newFragment;
		onScreenSwitched(screenName);

		mFragmentManager.beginTransaction()
				.attach(newFragment)
				.commitNow();

		return true;
	}

	public Fragment getCurrentFragment() {
		return mCurrentFragment;
	}

	private Fragment createFragment(String screenName, NavigationFactory navigationFactory) {
		Screen screen = createScreen(screenName);
		if(screen == null) {
			return null;
		}
		return navigationFactory.createFragment(screen);
	}
}
