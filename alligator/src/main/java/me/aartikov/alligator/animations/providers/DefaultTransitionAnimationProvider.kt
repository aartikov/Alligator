package me.aartikov.alligator.animations.providers

import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.Screen
import me.aartikov.alligator.TransitionType
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.TransitionAnimation

/**
 * Default implementation of [TransitionAnimationProvider]. Always returns `TransitionAnimation.DEFAULT`.
 */
class DefaultTransitionAnimationProvider : TransitionAnimationProvider {
    override fun getAnimation(
        transitionType: TransitionType,
        destinationType: DestinationType,
        screenClassFrom: Class<out Screen>,
        screenClassTo: Class<out Screen>,
        animationData: AnimationData?
    ): TransitionAnimation {
        return TransitionAnimation.DEFAULT
    }
}