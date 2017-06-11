package me.aartikov.alligator;

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
 * Helper class for getting a screen from its Android representation. Can be obtained with {@code getScreenResolver} method of {@link AndroidNavigator}.
 */
public class ScreenResolver {
	private NavigationFactory mNavigationFactory;

	ScreenResolver(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	/**
	 * Gets a screen from an activity.
	 *
	 * @param <ScreenT> screen type
	 * @param activity  activity containing a screen data in its intent
	 * @return a screen gotten from the activity intent
	 */
	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(Activity activity) {
		return (ScreenT) mNavigationFactory.getScreen(activity);
	}

	/**
	 * Gets a screen from a fragment.
	 *
	 * @param <ScreenT> screen type
	 * @param fragment  fragment containing a screen data in its arguments
	 * @return a screen gotten from the fragment
	 */
	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(Fragment fragment) {
		return (ScreenT) mNavigationFactory.getScreen(fragment);
	}

	/**
	 * Gets a screen from a dialog fragment.
	 *
	 * @param <ScreenT>      screen type
	 * @param dialogFragment dialog fragment containing a screen data in its arguments
	 * @return a screen gotten from the dialog fragment
	 */
	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment) {
		return (ScreenT) mNavigationFactory.getScreen(dialogFragment);
	}
}
