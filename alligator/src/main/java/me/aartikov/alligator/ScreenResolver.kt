package me.aartikov.alligator

import android.app.Activity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import me.aartikov.alligator.destinations.ActivityDestination
import me.aartikov.alligator.destinations.DialogFragmentDestination
import me.aartikov.alligator.destinations.FragmentDestination
import me.aartikov.alligator.navigationfactories.NavigationFactory

/**
 * Helps to get screens from activities and fragments.
 */
class ScreenResolver(private val mNavigationFactory: NavigationFactory) {
    /**
     * Gets a screen from an activity.
     *
     * @param <ScreenT> screen type
     * @param activity  activity containing a screen data in its intent
     * @return a screen gotten from the activity intent
     * @throws IllegalArgumentException if screen getting failed
    </ScreenT> */
    @Suppress("UNCHECKED_CAST")
    fun <ScreenT : Screen> getScreen(activity: Activity): ScreenT {
        val screen = getDestination(activity).getScreen(activity) as? ScreenT
        return screen
            ?: throw IllegalArgumentException("IntentConverter returns null for " + activity.javaClass.canonicalName)
    }

    /**
     * Gets a screen from an activity (nullable version). Note: it still can throw exceptions in some cases.
     *
     * @param <ScreenT> screen type
     * @param activity  activity
     * @return a screen gotten from the activity intent or null if there are no screen data in the activity.
     * @throws IllegalArgumentException if there are no screens registered for this activity.
    </ScreenT> */
    @Suppress("UNCHECKED_CAST")
    fun <ScreenT : Screen> getScreenOrNull(activity: Activity): ScreenT? {
        return getDestination(activity).getScreen(activity) as? ScreenT
    }

    /**
     * Gets a screen from a fragment.
     *
     * @param <ScreenT> screen type
     * @param fragment  fragment containing a screen data in its arguments
     * @return a screen gotten from the fragment
     * @throws IllegalArgumentException if screen getting failed
    </ScreenT> */
    @Suppress("UNCHECKED_CAST")
    fun <ScreenT : Screen> getScreen(fragment: Fragment): ScreenT {
        return getDestination(fragment).getScreen(fragment) as ScreenT
    }

    /**
     * Gets a screen from a dialog fragment.
     *
     * @param <ScreenT>      screen type
     * @param dialogFragment dialog fragment containing a screen data in its arguments
     * @return a screen gotten from the dialog fragment
     * @throws IllegalArgumentException if screen getting failed
    </ScreenT> */
    @Suppress("UNCHECKED_CAST")
    fun <ScreenT : Screen> getScreen(dialogFragment: DialogFragment): ScreenT {
        return getDestination(dialogFragment).getScreen(dialogFragment) as ScreenT
    }

    private fun getDestination(activity: Activity): ActivityDestination {
        val screenClass = mNavigationFactory.getScreenClass(activity)
            ?: throw IllegalArgumentException("Failed to get screen class from " + activity.javaClass.simpleName)
        val destination = mNavigationFactory.getDestination(screenClass)
        require(destination is ActivityDestination) { "Failed to get destination from " + activity.javaClass.simpleName }
        return destination
    }

    private fun getDestination(fragment: Fragment): FragmentDestination {
        val screenClass = mNavigationFactory.getScreenClass(fragment)
            ?: throw IllegalArgumentException("Failed to get screen class from " + fragment.javaClass.simpleName)
        val destination = mNavigationFactory.getDestination(screenClass)
        require(destination is FragmentDestination) { "Failed to get destination from " + fragment.javaClass.simpleName }
        return destination
    }

    private fun getDestination(dialogFragment: DialogFragment): DialogFragmentDestination {
        val screenClass = mNavigationFactory.getScreenClass(dialogFragment)
            ?: throw IllegalArgumentException("Failed to get screen class from " + dialogFragment.javaClass.simpleName)
        val destination = mNavigationFactory.getDestination(screenClass)
        require(destination is DialogFragmentDestination) { "Failed to get destination from " + dialogFragment.javaClass.simpleName }
        return destination
    }
}