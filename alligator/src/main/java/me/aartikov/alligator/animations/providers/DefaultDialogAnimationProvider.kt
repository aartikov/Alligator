package me.aartikov.alligator.animations.providers

import me.aartikov.alligator.Screen
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.DialogAnimation

/**
 * Default implementation of [DialogAnimationProvider]. Always returns `DialogAnimation.DEFAULT`.
 */
class DefaultDialogAnimationProvider : DialogAnimationProvider {
    override fun getAnimation(
        screenClass: Class<out Screen?>,
        animationData: AnimationData?
    ): DialogAnimation {
        return DialogAnimation.DEFAULT
    }
}