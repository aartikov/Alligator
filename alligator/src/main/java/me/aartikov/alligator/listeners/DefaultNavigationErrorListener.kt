package me.aartikov.alligator.listeners

import me.aartikov.alligator.exceptions.NavigationException

/**
 * Default implementation of [NavigationErrorListener]. Wraps [NavigationException] to `RuntimeException` and throws it.
 */
class DefaultNavigationErrorListener : NavigationErrorListener {
    override fun onNavigationError(e: NavigationException) {
        throw RuntimeException(e)
    }
}