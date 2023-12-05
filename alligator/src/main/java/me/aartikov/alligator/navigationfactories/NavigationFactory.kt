package me.aartikov.alligator.navigationfactories

import android.app.Activity
import androidx.fragment.app.Fragment
import me.aartikov.alligator.Screen
import me.aartikov.alligator.destinations.Destination

/**
 * Associates [Screen] with its Android implementation.
 */
interface NavigationFactory {
    /**
     * Returns [Destination] for a given screen.
     *
     * @param screenClass screen class
     * @return destination or null if there is no destination associated with this screen
     */
    fun getDestination(screenClass: Class<out Screen?>): Destination?

    /**
     * Retrieves screen class from an activity.
     *
     * @param activity activity
     * @return screen class or null if there is no screen class information in the activity.
     */
    fun getScreenClass(activity: Activity): Class<out Screen?>?

    /**
     * Retrieves screen class from a fragment.
     *
     * @param fragment fragment
     * @return screen class or null if there is no screen class information in the fragment.
     */
    fun getScreenClass(fragment: Fragment): Class<out Screen?>?

    /**
     * Returns screen class by a request code (for a screen that can return result).
     *
     * @param requestCode request code
     * @return screen class or null if there is no screen associated with this request code
     */
    fun getScreenClass(requestCode: Int): Class<out Screen?>?

    /**
     * Returns screen class for an previous activity in a back stack.
     *
     * @param activity current activity that stores information about previous screen class
     * @return screen class for an previous activity
     */
    fun getPreviousScreenClass(activity: Activity): Class<out Screen?>?
}