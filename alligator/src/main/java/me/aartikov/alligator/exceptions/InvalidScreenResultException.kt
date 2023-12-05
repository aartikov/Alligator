package me.aartikov.alligator.exceptions

/**
 * Exception thrown when a screen is requested to return unsupported result.
 */
class InvalidScreenResultException(message: String?) : NavigationException(message)