package me.aartikov.alligator.navigationfactories;


import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.destinations.Destination;

/**
 * Associates {@link Screen} with its Android implementation.
 */
public interface NavigationFactory {
	/**
	 * Returns {@link Destination} for a given screen.
	 *
	 * @param screenClass screen class
	 * @return destination or null if there is no destination associated with this screen
	 */
	@Nullable
	Destination getDestination(@NonNull Class<? extends Screen> screenClass);

	/**
	 * Retrieves screen class from an activity.
	 *
	 * @param activity activity
	 * @return screen class or null if there is no screen class information in the activity.
	 */
	@Nullable
	Class<? extends Screen> getScreenClass(@NonNull Activity activity);

	/**
	 * Retrieves screen class from a fragment.
	 *
	 * @param fragment fragment
	 * @return screen class or null if there is no screen class information in the fragment.
	 */
	@Nullable
	Class<? extends Screen> getScreenClass(@NonNull Fragment fragment);

	/**
	 * Returns screen class by a request code (for a screen that can return result).
	 *
	 * @param requestCode request code
	 * @return screen class or null if there is no screen associated with this request code
	 */
	@Nullable
	Class<? extends Screen> getScreenClass(int requestCode);

	/**
	 * Returns screen class for an previous activity in a back stack.
	 *
	 * @param activity current activity that stores information about previous screen class
	 * @return screen class for an previous activity
	 */
	@Nullable
	Class<? extends Screen> getPreviousScreenClass(@NonNull Activity activity);
}