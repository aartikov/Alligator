package me.aartikov.alligator.exceptions

/**
 * Exception thrown when fragment navigation was requested but it was not configured
 * with method [me.aartikov.alligator.NavigationContext.Builder.fragmentNavigation].
 */
class MissingFragmentNavigatorException :
    NavigationException("Fragment navigation is not configured. Do you forget to call fragmentNavigation method of NavigationContext.Builder?")