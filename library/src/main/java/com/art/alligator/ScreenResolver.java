package com.art.alligator;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * Date: 11.02.2017
 * Time: 16:27
 *
 * @author Artur Artikov
 */

/**
 * Helper class for getting screen back from its android representation
 */
public class ScreenResolver {
	private NavigationFactory mNavigationFactory;

	public ScreenResolver(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	/**
	 * Gets a screen from the activity
	 */
	public <ScreenT extends Screen> ScreenT getScreen(Activity activity, Class<ScreenT> screenClass) {
		return mNavigationFactory.getScreen(activity.getIntent(), screenClass);
	}

	/**
	 * Gets a screen from the fragment
	 */
	public <ScreenT extends Screen> ScreenT getScreen(Fragment fragment, Class<ScreenT> screenClass) {
		return mNavigationFactory.getScreen(fragment, screenClass);
	}

	/**
	 * Gets a screen from the dialog fragment
	 */
	public <ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment, Class<ScreenT> screenClass) {
		return mNavigationFactory.getScreen(dialogFragment, screenClass);
	}
}
