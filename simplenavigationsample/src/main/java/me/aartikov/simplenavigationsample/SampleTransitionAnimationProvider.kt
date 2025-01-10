package me.aartikov.simplenavigationsample

import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.Screen
import me.aartikov.alligator.TransitionType
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.SimpleTransitionAnimation
import me.aartikov.alligator.animations.TransitionAnimation
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider

// Lets make a nice slide animation.
class SampleTransitionAnimationProvider : TransitionAnimationProvider {
    override fun getAnimation(
        transitionType: TransitionType,
        destinationType: DestinationType,
        screenClassFrom: Class<out Screen?>,
        screenClassTo: Class<out Screen?>,
        animationData: AnimationData?
    ): TransitionAnimation {
        return when (transitionType) {
            TransitionType.FORWARD -> SimpleTransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left)
            TransitionType.BACK -> SimpleTransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right)
            else -> TransitionAnimation.DEFAULT
        }
    }
}
