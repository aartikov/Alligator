package me.aartikov.alligator.animations.providers

import me.aartikov.alligator.Screen
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.DialogAnimation

/**
 * Provider of a [DialogAnimation].
 */
interface DialogAnimationProvider {
    /**
     * Is called when a [DialogAnimation] is needed to show a screen represented by a dialog fragment.
     *
     * @param screenClass   a class of a shown screen
     * @param animationData data for an additional animation configuring
     * @return an animation that will be used to show a dialog fragment
     */
    fun getAnimation(
        screenClass: Class<out Screen?>,
        animationData: AnimationData?
    ): DialogAnimation
}