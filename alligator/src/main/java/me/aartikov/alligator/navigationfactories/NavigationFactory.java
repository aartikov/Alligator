package me.aartikov.alligator.navigationfactories;

/**
 * Date: 11.02.2017
 * Time: 11:37
 *
 * @author Artur Artikov
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.screenimplementations.ScreenImplementation;

/**
 * Associates {@link Screen} with its Android implementation.
 */
public interface NavigationFactory {
	/**
	 * Returns {@link ScreenImplementation} for a given screen.
	 *
	 * @param screenClass screen class
	 * @return screen implementation or null if there is no implementation associated with this screen
	 */
	@Nullable
	ScreenImplementation getScreenImplementation(@NonNull Class<? extends Screen> screenClass);

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