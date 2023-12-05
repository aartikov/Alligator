package me.aartikov.alligator.listeners

import me.aartikov.alligator.exceptions.NavigationException

/**
 * Interface for navigation error handling.
 */
interface NavigationErrorListener {
    /**
     * Is called when an error has occurred during [Command] execution.
     *
     * @param e exception with information about an error
     */
    fun onNavigationError(e: NavigationException)
}