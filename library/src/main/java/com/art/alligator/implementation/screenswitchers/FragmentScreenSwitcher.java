package com.art.alligator.implementation.screenswitchers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

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
	private Fragment mCurrentFragment;

	public FragmentScreenSwitcher(FragmentManager fragmentManager, int containerId) {
		mFragmentManager = fragmentManager;
		mContainerId = containerId;
		mCurrentFragment = mFragmentManager.findFragmentById(mContainerId);
	}

	protected abstract Fragment createFragment(String screenName);

	protected void onScreenSwitched(String screenName) {
	}

	@Override
	public boolean switchTo(String screenName) {
		Fragment newFragment = mFragmentManager.findFragmentByTag(screenName);
		if (newFragment == null) {
			newFragment = createFragment(screenName);
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
}
