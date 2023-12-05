package me.aartikov.alligator.exceptions

/**
 * Exception thrown when flow fragment navigation was requested but it was not configured
 * with method [me.aartikov.alligator.NavigationContext.Builder.flowFragmentNavigation].
 */
class MissingFlowFragmentNavigatorException :
    NavigationException("Flow fragment navigation is not configured. Do you forget to call flowFragmentNavigation method of NavigationContext.Builder?")