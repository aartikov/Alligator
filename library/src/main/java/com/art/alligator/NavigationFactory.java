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

public interface NavigationFactory {
	/**
	 * Get Screen ViewType
	 */
	ViewType getViewType(Class<? extends Screen> screenClass);

	/**
	 * Returns activity class for the screen class
	 */
	Class<? extends Activity> getActivityClass(Class<? extends Screen> screenClass);

	/**
	 * Creates an activity intent for the given screen
	 */
	Intent createActivityIntent(Context context, Screen screen);

	/**
	 * Gets screen from the intent
	 */
	<ScreenT extends Screen> ScreenT getScreen(Intent intent, Class<ScreenT> screenClass);

	/**
	 * Creates an fragment for the given screen
	 */
	Fragment createFragment(Screen screen);

	/**
	 * Gets screen from the fragment
	 */
	<ScreenT extends Screen> ScreenT getScreen(Fragment fragment, Class<ScreenT> screenClass);

	/**
	 * Creates an dialog fragment for the given screen
	 */
	DialogFragment createDialogFragment(Screen screen);

	/**
	 * Gets screen from the dialog fragment
	 */
	<ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment, Class<ScreenT> screenClass);

	/**
	 * Checks if a screen can be started for result
	 */
	boolean isScreenForResult(Class<? extends Screen> screenClass);

	/**
	 * Returns request code for the screen class
	 */
	int getRequestCode(Class<? extends Screen> screenClass);

	/**
	 * Returns screen result class for the screen class
	 */
	Class<? extends ScreenResult> getScreenResultClass(Class<? extends Screen> screenClass);

	/**
	 * Creates ActivityResult from ScreenResult
	 */
	ActivityResult createActivityResult(Class<? extends Screen> screenClass, ScreenResult screenResult);

	/**
	 * Gets ScreenResult from ActivityResult
	 */
	ScreenResult getScreenResult(Class<? extends Screen> screenClass, ActivityResult activityResult);

	/**
	 * Gets known screen classes
	 */
	Collection<Class<? extends Screen>> getScreenClasses();
}
