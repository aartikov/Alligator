package me.aartikov.alligator.exceptions

import me.aartikov.alligator.Screen

/**
 * Exception thrown when an implicit intent can't be resolved.
 */
class ActivityResolvingException(val screen: Screen) :
    NavigationException("Failed to resolve an activity for a screen " + screen.javaClass.simpleName)