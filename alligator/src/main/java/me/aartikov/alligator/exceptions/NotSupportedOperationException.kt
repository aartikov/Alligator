package me.aartikov.alligator.exceptions

/**
 * Exception thrown when unsupported operation is requested.
 */
class NotSupportedOperationException(message: String) : NavigationException(message)