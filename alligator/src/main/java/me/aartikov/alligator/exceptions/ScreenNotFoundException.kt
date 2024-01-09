package me.aartikov.alligator.exceptions

import me.aartikov.alligator.Screen

/**
 * Exception thrown when a screen is not found in [me.aartikov.alligator.helpers.FragmentStack].
 */
class ScreenNotFoundException(val screenClass: Class<out Screen>) :
    NavigationException("Screen " + screenClass.simpleName + " is not found.")