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

/**
 * Fragment screen switcher that uses a {@link NavigationFactory} for fragment creation.
 */
public abstract class FactoryFragmentScreenSwitcher extends FragmentScreenSwitcher {
	private NavigationFactory mNavigationFactory;

	/**
	 * @param fragmentManager   fragment manager used for fragment transactions
	 * @param containerId       id of a container where fragments will be added
	 * @param navigationFactory navigation factory used for fragment creation
	 */
	public FactoryFragmentScreenSwitcher(FragmentManager fragmentManager, int containerId, NavigationFactory navigationFactory) {
		super(fragmentManager, containerId);
		mNavigationFactory = navigationFactory;
	}

	/**
	 * Returns a screen by a screen name.
	 *
	 * @param screenName screen name
	 * @return screen. It must be represented by a fragment.
	 */
	abstract protected Screen getScreen(String screenName);

	@Override
	final protected Fragment createFragment(String screenName) {
		Screen screen = getScreen(screenName);
		return screen != null ? mNavigationFactory.createFragment(screen) : null;
	}
}
