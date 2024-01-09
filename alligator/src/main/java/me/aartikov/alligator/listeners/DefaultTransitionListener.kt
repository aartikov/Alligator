package me.aartikov.alligator.listeners

import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.Screen
import me.aartikov.alligator.TransitionType

/**
 * Transition listener that does nothing.
 */
class DefaultTransitionListener : TransitionListener {
    override fun onScreenTransition(
        transitionType: TransitionType,
        destinationType: DestinationType,
        screenClassFrom: Class<out Screen>?,
        screenClassTo: Class<out Screen>?
    ) {
    }
}