package me.aartikov.alligator.exceptions

/**
 * Exception thrown when a command requires a [me.aartikov.alligator.screenswitchers.ScreenSwitcher] but it is not set to [me.aartikov.alligator.NavigationContext].
 */
class MissingScreenSwitcherException(message: String?) : NavigationException(message)