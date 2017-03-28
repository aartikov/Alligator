package com.art.alligator;

import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * Date: 11.02.2017
 * Time: 11:37
 *
 * @author Artur Artikov
 */

/**
 * Interface for converting screens to theirs android representation (intents, fragments, dialog fragment) and vice versa.
 * It is also responsible for converting ScreenResult to ActivityResult and vice versa.
 */
public interface NavigationFactory {
	/**
	 * Get Screen ViewType
	 */
	ViewType getViewType(Class<? extends Screen> screenClass);

	/**
	 * Returns activity class for the screen class
	 * @throws IllegalArgumentException if screen is not represented by Activity
	 */
	Class<? extends Activity> getActivityClass(Class<? extends Screen> screenClass);

	/**
	 * Creates an activity intent for the given screen
	 * @throws IllegalArgumentException if screen is not represented by Activity
	 */
	Intent createActivityIntent(Context context, Screen screen);

	/**
	 * Gets a screen from the intent
	 */
	<ScreenT extends Screen> ScreenT getScreen(Intent intent, Class<ScreenT> screenClass);

	/**
	 * Creates an fragment for the given screen
	 * @throws IllegalArgumentException if screen is not represented by Fragment
	 */
	Fragment createFragment(Screen screen);

	/**
	 * Gets a screen from the fragment
	 */
	<ScreenT extends Screen> ScreenT getScreen(Fragment fragment, Class<ScreenT> screenClass);

	/**
	 * Creates an dialog fragment for the given screen
	 * @throws IllegalArgumentException if screen is not represented by DialogFragment
	 */
	DialogFragment createDialogFragment(Screen screen);

	/**
	 * Gets a screen from the dialog fragment
	 */
	<ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment, Class<ScreenT> screenClass);

	/**
	 * Checks if a screen can be started for result
	 */
	boolean isScreenForResult(Class<? extends Screen> screenClass);

	/**
	 * Returns a request code for the screen class
	 * @throws IllegalArgumentException if screen can't be started for result
	 */
	int getRequestCode(Class<? extends Screen> screenClass);

	/**
	 * Returns a screen result class for the screen class
	 * @throws IllegalArgumentException if screen can't be started for result
	 */
	Class<? extends ScreenResult> getScreenResultClass(Class<? extends Screen> screenClass);

	/**
	 * Creates ActivityResult from ScreenResult
	 * @throws IllegalArgumentException if screen can't be started for result
	 */
	ActivityResult createActivityResult(Class<? extends Screen> screenClass, ScreenResult screenResult);

	/**
	 * Gets ScreenResult from ActivityResult
	 * @throws IllegalArgumentException if screen can't be started for result
	 */
	ScreenResult getScreenResult(Class<? extends Screen> screenClass, ActivityResult activityResult);

	/**
	 * Gets known screen classes
	 */
	Collection<Class<? extends Screen>> getScreenClasses();
}
