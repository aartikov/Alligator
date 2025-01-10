package me.aartikov.advancedscreenswitchersample

import me.aartikov.alligator.Screen
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.SimpleTransitionAnimation
import me.aartikov.alligator.animations.TransitionAnimation
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher.AnimationProvider

class SampleScreenSwitcherAnimationProvider(private val mTabScreens: List<Screen>) : AnimationProvider {

    override fun getAnimation(
        screenFrom: Screen,
        screenTo: Screen,
        animationData: AnimationData?
    ): TransitionAnimation {
        val indexFrom = mTabScreens.indexOf(screenFrom)
        val indexTo = mTabScreens.indexOf(screenTo)
        return if (indexTo > indexFrom) {
            SimpleTransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left)
        } else {
            SimpleTransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}
