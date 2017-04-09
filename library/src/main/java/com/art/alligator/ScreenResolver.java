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
 * Helper class for getting a screen from its Android representation.
 */
public class ScreenResolver {
	private NavigationFactory mNavigationFactory;

	public ScreenResolver(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	/**
	 * Gets a screen from an activity.
	 *
	 * @param <ScreenT>   screen type
	 * @param activity    activity containing a screen data in its intent
	 * @param screenClass screen class
	 * @return a screen gotten from the activity intent
	 */
	public <ScreenT extends Screen> ScreenT getScreen(Activity activity, Class<ScreenT> screenClass) {
		return mNavigationFactory.getScreen(activity.getIntent(), screenClass);
	}

	/**
	 * Gets a screen from a fragment
	 *
	 * @param <ScreenT>   screen type
	 * @param fragment    fragment containing a screen data in its arguments
	 * @param screenClass screen class
	 * @return a screen gotten from the fragment
	 */
	public <ScreenT extends Screen> ScreenT getScreen(Fragment fragment, Class<ScreenT> screenClass) {
		return mNavigationFactory.getScreen(fragment, screenClass);
	}

	/**
	 * Gets a screen from a dialog fragment
	 *
	 * @param <ScreenT>      screen type
	 * @param dialogFragment dialog fragment containing a screen data in its arguments
	 * @param screenClass    screen class
	 * @return a screen gotten from the dialog fragment
	 */
	public <ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment, Class<ScreenT> screenClass) {
		return mNavigationFactory.getScreen(dialogFragment, screenClass);
	}
}
