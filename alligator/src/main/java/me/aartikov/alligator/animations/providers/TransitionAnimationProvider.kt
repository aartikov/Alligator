package me.aartikov.alligator.animations.providers

import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.Screen
import me.aartikov.alligator.TransitionType
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.TransitionAnimation

/**
 * Provider of a [TransitionAnimation].
 */
interface TransitionAnimationProvider {
    /**
     * Is called when a [TransitionAnimation] is needed to make a transition from one screen to another.
     *
     * @param transitionType  type of a transition
     * @param destinationType destination type of screens involved in the transition
     * @param screenClassFrom class of the screen that disappears during a transition
     * @param screenClassTo   class of the screen that appears during a transition
     * @param animationData   data for an additional animation configuring
     * @return an animation that will be used for a transition
     */
    fun getAnimation(
        transitionType: TransitionType,
        destinationType: DestinationType,
        screenClassFrom: Class<out Screen>,
        screenClassTo: Class<out Screen>,
        animationData: AnimationData?
    ): TransitionAnimation
}