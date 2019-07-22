package exp.terenfear.singleactivitynavigation

import me.aartikov.alligator.Screen
import me.aartikov.alligator.TransitionType
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.SimpleTransitionAnimation
import me.aartikov.alligator.animations.TransitionAnimation
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider

/**
 * Date: 28.02.2017
 * Time: 21:01
 *
 * @author Artur Artikov
 */
class SampleTransitionAnimationProvider : TransitionAnimationProvider {
    override fun getAnimation(transitionType: TransitionType, screenClassFrom: Class<out Screen>, screenClassTo: Class<out Screen>,
                              isActivity: Boolean, animationData: AnimationData?): TransitionAnimation {
        return if (isActivity) {
            TransitionAnimation.DEFAULT
        } else {
            SimpleTransitionAnimation(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}