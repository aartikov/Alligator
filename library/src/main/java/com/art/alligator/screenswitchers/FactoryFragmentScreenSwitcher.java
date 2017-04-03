package com.art.alligator.screenswitchers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;

/**
 * Date: 03.04.2017
 * Time: 20:23
 *
 * @author Artur Artikov
 */

public abstract class FactoryFragmentScreenSwitcher extends FragmentScreenSwitcher {
	private NavigationFactory mNavigationFactory;

	public FactoryFragmentScreenSwitcher(FragmentManager fragmentManager, int containerId, NavigationFactory navigationFactory) {
		super(fragmentManager, containerId);
		mNavigationFactory = navigationFactory;
	}

	abstract protected Screen getScreen(String screenName);

	@Override
	final protected Fragment createFragment(String screenName) {
		Screen screen = getScreen(screenName);
		return screen != null ? mNavigationFactory.createFragment(screen) : null;
	}
}
