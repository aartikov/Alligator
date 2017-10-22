package me.aartikov.alligator;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;

/**
 * Date: 22.10.2017
 * Time: 10:34
 *
 * @author Artur Artikov
 */

public class ScreenResolver {
	private NavigationFactory mNavigationFactory;

	public ScreenResolver(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	/**
	 * Gets a screen from an activity.
	 *
	 * @param <ScreenT> screen type
	 * @param activity  activity containing a screen data in its intent
	 * @return a screen gotten from the activity intent
	 * @throws IllegalArgumentException if screen getting failed
	 */

	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(Activity activity) {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(activity);
		if (screenClass == null) {
			throw new IllegalArgumentException("Failed to get screen class from " + activity.getClass().getSimpleName());
		}

		ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screenClass);
		if (!(screenImplementation instanceof ActivityScreenImplementation)) {
			throw new IllegalArgumentException("Failed to get screen implementation from " + activity.getClass().getSimpleName());
		}

		return (ScreenT) ((ActivityScreenImplementation) screenImplementation).getScreen(activity);
	}


	/**
	 * Gets a screen from a fragment.
	 *
	 * @param <ScreenT> screen type
	 * @param fragment  fragment containing a screen data in its arguments
	 * @return a screen gotten from the fragment
	 * @throws IllegalArgumentException if screen getting failed
	 */

	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(Fragment fragment) {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(fragment);
		if (screenClass == null) {
			throw new IllegalArgumentException("Failed to get screen class from " + fragment.getClass().getSimpleName());
		}

		ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screenClass);
		if (!(screenImplementation instanceof FragmentScreenImplementation)) {
			throw new IllegalArgumentException("Failed to get screen implementation from " + fragment.getClass().getSimpleName());
		}

		return (ScreenT) ((FragmentScreenImplementation) screenImplementation).getScreen(fragment);
	}


	/**
	 * Gets a screen from a dialog fragment.
	 *
	 * @param <ScreenT>      screen type
	 * @param dialogFragment dialog fragment containing a screen data in its arguments
	 * @return a screen gotten from the dialog fragment
	 * @throws IllegalArgumentException if screen getting failed
	 */

	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment) {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(dialogFragment);
		if (screenClass == null) {
			throw new IllegalArgumentException("Failed to get screen class from " + dialogFragment.getClass().getSimpleName());
		}

		ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screenClass);
		if (!(screenImplementation instanceof DialogFragmentScreenImplementation)) {
			throw new IllegalArgumentException("Failed to get screen implementation from " + dialogFragment.getClass().getSimpleName());
		}

		return (ScreenT) ((DialogFragmentScreenImplementation) screenImplementation).getScreen(dialogFragment);
	}
}