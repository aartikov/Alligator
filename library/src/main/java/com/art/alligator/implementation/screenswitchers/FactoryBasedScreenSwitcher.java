package com.art.alligator.implementation.screenswitchers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;

/**
 * Date: 01/30/2016
 * Time: 10:13
 *
 * @author Artur Artikov
 */
public abstract class FactoryBasedScreenSwitcher extends FragmentScreenSwitcher {
	private NavigationFactory mNavigationFactory;

	public FactoryBasedScreenSwitcher(FragmentManager fragmentManager, int containerId, NavigationFactory navigationFactory) {
		super(fragmentManager, containerId);
		mNavigationFactory = navigationFactory;
	}

	protected abstract Screen getScreen(String screenName);

	protected final Fragment createFragment(String screenName) {
		Screen screen = getScreen(screenName);
		if (screen == null) {
			return null;
		}
		return mNavigationFactory.createFragment(screen);
	}
}
