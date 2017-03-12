package com.art.alligator;

import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Date: 11.02.2017
 * Time: 11:37
 *
 * @author Artur Artikov
 */

public interface NavigationFactory {
	/**
	 * Creates an activity intent for the given screen, or returns null if the screen is not represented by an activity
	 */
	Intent createActivityIntent(Context context, Screen screen);

	/**
	 * Returns activity class for the screen class or null if the screen is not represented by an activity
	 */
	Class<? extends Activity> getActivityClass(Class<? extends Screen> screenClass);

	/**
	 * Creates an fragment for the given screen, or returns  null if the screen is not represented by a fragment
	 */
	Fragment createFragment(Screen screen);

	/**
	 * Returns request code for the screen class or -1 if the screen is not represented by an activity
	 */
	int getRequestCode(Class<? extends Screen> screenClass);

	/**
	 * Convert ActivityResult to ScreenResult
	 */
	ScreenResult createScreenResult(ActivityResult activityResult);

	/**
	 * Convert ScreenResult to ActivityResult
	 */
	ActivityResult createActivityResult(ScreenResult screenResult);

	/**
	 * Get known screen classes
	 */
	Collection<Class<? extends Screen>> getScreenClasses();
}
