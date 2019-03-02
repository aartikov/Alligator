package me.aartikov.alligator;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.ScreenImplementation;

/**
 * Date: 22.10.2017
 * Time: 10:34
 *
 * @author Artur Artikov
 */

/**
 * Helps to get screens from activities and fragments.
 */
public class ScreenResolver {
	private NavigationFactory mNavigationFactory;

	public ScreenResolver(@NonNull NavigationFactory navigationFactory) {
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
	@NonNull
	public <ScreenT extends Screen> ScreenT getScreen(@NonNull Activity activity) {
		ScreenT screen = (ScreenT) getScreenImplementation(activity).getScreen(activity);
		if (screen != null) {
			return screen;
		} else {
			throw new IllegalArgumentException("IntentConverter returns null for " + activity.getClass().getCanonicalName());
		}
	}

	/**
	 * Gets a screen from an activity (nullable version). Note: it still can throw exceptions in some cases.
	 *
	 * @param <ScreenT> screen type
	 * @param activity  activity
	 * @return a screen gotten from the activity intent or null if there are no screen data in the activity.
	 * @throws IllegalArgumentException if there are no screens registered for this activity.
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public <ScreenT extends Screen> ScreenT getScreenOrNull(@NonNull Activity activity) {
		return (ScreenT) getScreenImplementation(activity).getScreen(activity);
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
	@NonNull
	public <ScreenT extends Screen> ScreenT getScreen(@NonNull Fragment fragment) {
		return (ScreenT) getScreenImplementation(fragment).getScreen(fragment);
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
	@NonNull
	public <ScreenT extends Screen> ScreenT getScreen(@NonNull DialogFragment dialogFragment) {
		return (ScreenT) getScreenImplementation(dialogFragment).getScreen(dialogFragment);
	}

	@NonNull
	private ActivityScreenImplementation getScreenImplementation(@NonNull Activity activity) {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(activity);
		if (screenClass == null) {
			throw new IllegalArgumentException("Failed to get screen class from " + activity.getClass().getSimpleName());
		}

		ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screenClass);
		if (!(screenImplementation instanceof ActivityScreenImplementation)) {
			throw new IllegalArgumentException("Failed to get screen implementation from " + activity.getClass().getSimpleName());
		}

		return (ActivityScreenImplementation) screenImplementation;
	}

	@NonNull
	private FragmentScreenImplementation getScreenImplementation(@NonNull Fragment fragment) {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(fragment);
		if (screenClass == null) {
			throw new IllegalArgumentException("Failed to get screen class from " + fragment.getClass().getSimpleName());
		}

		ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screenClass);
		if (!(screenImplementation instanceof FragmentScreenImplementation)) {
			throw new IllegalArgumentException("Failed to get screen implementation from " + fragment.getClass().getSimpleName());
		}

		return (FragmentScreenImplementation) screenImplementation;
	}

	@NonNull
	private DialogFragmentScreenImplementation getScreenImplementation(@NonNull DialogFragment dialogFragment) {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(dialogFragment);
		if (screenClass == null) {
			throw new IllegalArgumentException("Failed to get screen class from " + dialogFragment.getClass().getSimpleName());
		}

		ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screenClass);
		if (!(screenImplementation instanceof DialogFragmentScreenImplementation)) {
			throw new IllegalArgumentException("Failed to get screen implementation from " + dialogFragment.getClass().getSimpleName());
		}

		return (DialogFragmentScreenImplementation) screenImplementation;
	}
}