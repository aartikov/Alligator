package me.aartikov.alligator;

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
	 * Gets a screen class from an activity.
	 *
	 * @param activity activity
	 * @return a screen class gotten from the activity or null if there is no information about a screen class
	 */
	Class<? extends Screen> getScreenClass(Activity activity);

	/**
	 * Gets a screen from an intent.
	 *
	 * @param activity      activity containing a screen data in its intent
	 * @return a screen gotten from the intent
	 * @throws IllegalArgumentException if screen getting failed
	 */
	Screen getScreen(Activity activity);

	/**
	 * Creates a fragment for a screen.
	 *
	 * @param screen screen
	 * @return a created fragment
	 * @throws IllegalArgumentException if the screen is not represented by a fragment
	 */
	Fragment createFragment(Screen screen);

	/**
	 * Gets a screen class from a fragment.
	 *
	 * @param fragment fragment
	 * @return a screen class gotten from the fragment or null if there is no information about a screen class
	 */
	Class<? extends Screen> getScreenClass(Fragment fragment);

	/**
	 * Gets a screen from a fragment.
	 *
	 * @param fragment    fragment containing a screen data in its arguments
	 * @return a screen gotten from the fragment
	 * @throws IllegalArgumentException if screen getting failed
	 */
	Screen getScreen(Fragment fragment);

	/**
	 * Creates a dialog fragment for a screen.
	 *
	 * @param screen screen
	 * @return a created dialog fragment
	 * @throws IllegalArgumentException if the screen is not represented by a dialog fragment
	 */
	DialogFragment createDialogFragment(Screen screen);

	/**
	 * Gets a screen class from a dialog fragment.
	 *
	 * @param dialogFragment dialog fragment
	 * @return a screen class gotten from the dialog fragment or null if there is no information about a screen class
	 */
	Class<? extends Screen> getScreenClass(DialogFragment dialogFragment);

	/**
	 * Gets a screen from a dialog fragment.
	 *
	 * @param dialogFragment dialog fragment containing a screen data in its arguments
	 * @return a screen gotten from the dialog fragment
	 * @throws IllegalArgumentException if screen getting failed
	 */
	Screen getScreen(DialogFragment dialogFragment);

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
	 * Gets a screen class by a request code.
	 *
	 * @param requestCode request code
	 * @return a screen class gotten by the request code or null if there is no screen with this request code
	 */
	Class<? extends Screen> getScreenClass(int requestCode);

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
}
