package me.aartikov.alligator.listeners

import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.Screen
import me.aartikov.alligator.TransitionType

/**
 * Interface for listening of screen transition.
 */
interface TransitionListener {
    /**
     * Is called when an usual screen transition (not screen switching and not dialog showing) has been executed.
     *
     * @param transitionType  type of a transition
     * @param destinationType destination type of screens involved in the transition
     * @param screenClassFrom class of the screen that disappears during a transition or `null` if there was no current screen before transition
     * @param screenClassTo   class of the screen that appears during a transition or `null` if there was no previous screen before transition
     */
    fun onScreenTransition(
        transitionType: TransitionType,
        destinationType: DestinationType,
        screenClassFrom: Class<out Screen?>?,
        screenClassTo: Class<out Screen?>?
    )
}