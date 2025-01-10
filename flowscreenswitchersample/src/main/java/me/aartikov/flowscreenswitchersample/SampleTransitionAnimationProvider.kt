package me.aartikov.flowscreenswitchersample

import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.Screen
import me.aartikov.alligator.TransitionType
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.SimpleTransitionAnimation
import me.aartikov.alligator.animations.TransitionAnimation
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider

class SampleTransitionAnimationProvider : TransitionAnimationProvider {
    override fun getAnimation(
        transitionType: TransitionType,
        destinationType: DestinationType,
        screenClassFrom: Class<out Screen?>,
        screenClassTo: Class<out Screen?>,
        animationData: AnimationData?
    ): TransitionAnimation {
        return if (destinationType == DestinationType.ACTIVITY) {
            TransitionAnimation.DEFAULT
        } else {
            SimpleTransitionAnimation(R.anim.stay, R.anim.fade_out)
        }
    }
}