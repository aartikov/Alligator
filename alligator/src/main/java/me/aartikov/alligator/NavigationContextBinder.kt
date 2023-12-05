package me.aartikov.alligator

import androidx.appcompat.app.AppCompatActivity

/**
 * Interface for binding and unbinding of a [NavigationContext].
 */
interface NavigationContextBinder {
    /**
     * Returns bound navigation context
     *
     * @return navigation context if it is bound or null otherwise
     */
    val navigationContext: NavigationContext?

    /**
     * Returns if a navigation context is bound.
     *
     * @return true if a navigation context is bound
     */
    val isBound: Boolean

    /**
     * Bind a navigation context if no context is bound or a context with the same activity is bound. Do nothing otherwise.
     * This method should be called from `onResumeFragments` of an activity.
     *
     * @param navigationContext navigation context
     */
    fun bind(navigationContext: NavigationContext)

    /**
     * Unbind a currently bound navigation context if it has the same activity as a passed one. Do nothing otherwise.
     * This method should be called from `onPause` of an activity.
     *
     * @param activity activity that initiates unbinding
     */
    fun unbind(activity: AppCompatActivity?)
}