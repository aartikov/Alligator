package me.aartikov.alligator.listeners

import me.aartikov.alligator.exceptions.NavigationException

/**
 * Interface for navigation error handling.
 */
fun interface NavigationErrorListener {
    /**
     * Is called when an error has occurred during [me.aartikov.alligator.commands.Command] execution.
     *
     * @param e exception with information about an error
     */
    fun onNavigationError(e: NavigationException)
}