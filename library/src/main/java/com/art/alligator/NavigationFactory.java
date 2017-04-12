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
 * Interface for converting {@link Screen} and {@link ScreenResult} to theirs Android representation (intents, fragments, dialog fragments for {@link Screen},
 * {@link ActivityResult} for {@link ScreenResult}) and vice versa.
 */
public interface NavigationFactory {
	/**
	 * Returns a {@link ViewType} of a screen.
	 *
	 * @param screenClass screen class
	 * @return corresponding {@link ViewType} of the screen, or {@code ViewType.UNKNOWN} if the screen is unknown
	 */
	ViewType getViewType(Class<? extends Screen> screenClass);

	/**
	 * Returns an activity class that represents a screen.
	 *
	 * @param screenClass screen class
	 * @return a class of the activity that represents the screen
	 * @throws IllegalArgumentException if the screen is not represented by an activity
	 */
	Class<? extends Activity> getActivityClass(Class<? extends Screen> screenClass);

	/**
	 * Creates an intent that starts an activity for a screen.
	 *
	 * @param context context used to create an intent
	 * @param screen  screen
	 * @return a created intent
	 * @throws IllegalArgumentException if the screen is not represented by an activity
	 */
	Intent createActivityIntent(Context context, Screen screen);

	/**
	 * Gets a screen from an intent.
	 *
	 * @param <ScreenT>   screen type
	 * @param intent      intent containing a screen data in its extra
	 * @param screenClass screen class
	 * @return a screen gotten from the intent
	 * @throws IllegalArgumentException if screen getting failed
	 */
	<ScreenT extends Screen> ScreenT getScreen(Intent intent, Class<ScreenT> screenClass);

	/**
	 * Creates a fragment for a screen.
	 *
	 * @param screen screen
	 * @return a created fragment
	 * @throws IllegalArgumentException if the screen is not represented by a fragment
	 */
	Fragment createFragment(Screen screen);

	/**
	 * Gets a screen from a fragment.
	 *
	 * @param <ScreenT>   screen type
	 * @param fragment    fragment containing a screen data in its arguments
	 * @param screenClass screen class
	 * @return a screen gotten from the fragment
	 * @throws IllegalArgumentException if screen getting failed
	 */
	<ScreenT extends Screen> ScreenT getScreen(Fragment fragment, Class<ScreenT> screenClass);

	/**
	 * Creates a dialog fragment for a screen.
	 *
	 * @param screen screen
	 * @return a created dialog fragment
	 * @throws IllegalArgumentException if the screen is not represented by a dialog fragment
	 */
	DialogFragment createDialogFragment(Screen screen);

	/**
	 * Gets a screen from a dialog fragment.
	 *
	 * @param <ScreenT>      screen type
	 * @param dialogFragment dialog fragment containing a screen data in its arguments
	 * @param screenClass    screen class
	 * @return a screen gotten from the dialog fragment
	 * @throws IllegalArgumentException if screen getting failed
	 */
	<ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment, Class<ScreenT> screenClass);

	/**
	 * Checks if a screen can return a result.
	 *
	 * @param screenClass screen class
	 * @return true if the screen can return a result
	 */
	boolean isScreenForResult(Class<? extends Screen> screenClass);

	/**
	 * Returns a request code for a screen.
	 * <p>
	 * This request code is unique for each screen class and can be used in {@code startActivityForResult}.
	 *
	 * @param screenClass screen class
	 * @return a request code for the screen
	 * @throws IllegalArgumentException if screen can't return a result
	 */
	int getRequestCode(Class<? extends Screen> screenClass);

	/**
	 * Returns a screen result class for the screen.
	 *
	 * @param screenClass screen class
	 * @return class of the result that the screen can return
	 * @throws IllegalArgumentException if screen can't return a result
	 */
	Class<? extends ScreenResult> getScreenResultClass(Class<? extends Screen> screenClass);

	/**
	 * Creates an {@link ActivityResult} from a {@link ScreenResult}.
	 *
	 * @param screenClass  class of the screen that returned a result
	 * @param screenResult screen result
	 * @return an activity result created from the screen result
	 * @throws IllegalArgumentException if screen can't return a result
	 */
	ActivityResult createActivityResult(Class<? extends Screen> screenClass, ScreenResult screenResult);

	/**
	 * Gets a {@link ScreenResult} from an {@link ActivityResult}.
	 *
	 * @param screenClass    class of the screen that returned a result
	 * @param activityResult activity result
	 * @return a screen result gotten from the activity result
	 * @throws IllegalArgumentException if screen can't return a result
	 */
	ScreenResult getScreenResult(Class<? extends Screen> screenClass, ActivityResult activityResult);

	/**
	 * Gets known screen classes.
	 *
	 * @return a collection of screens known to the navigation factory
	 */
	Collection<Class<? extends Screen>> getScreenClasses();
}
