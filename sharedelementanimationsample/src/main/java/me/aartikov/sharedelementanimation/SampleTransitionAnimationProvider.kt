package me.aartikov.sharedelementanimation

import android.annotation.SuppressLint
import android.os.Build
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.Screen
import me.aartikov.alligator.TransitionType
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.LollipopTransitionAnimation
import me.aartikov.alligator.animations.SimpleTransitionAnimation
import me.aartikov.alligator.animations.TransitionAnimation
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider
import me.aartikov.sharedelementanimation.ui.SharedElementProvider

class SampleTransitionAnimationProvider(private val mActivity: AppCompatActivity) : TransitionAnimationProvider {

    override fun getAnimation(
        transitionType: TransitionType,
        destinationType: DestinationType,
        screenClassFrom: Class<out Screen?>,
        screenClassTo: Class<out Screen?>,
        animationData: AnimationData?
    ): TransitionAnimation {
        return when (transitionType) {
            TransitionType.FORWARD -> {
                createSlideAnimation(true, animationData)
            }
            TransitionType.BACK -> {
                createSlideAnimation(false, animationData)
            }
            else -> {
                TransitionAnimation.DEFAULT
            }
        }
    }

    @SuppressLint("RtlHardcoded")
    private fun createSlideAnimation(forward: Boolean, animationData: AnimationData?): TransitionAnimation {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val enterTransition: Transition = if (forward) Slide(Gravity.RIGHT) else Slide(Gravity.LEFT)
            val exitTransition: Transition = if (forward) Slide(Gravity.LEFT) else Slide(Gravity.RIGHT)
            val animation = LollipopTransitionAnimation(enterTransition, exitTransition)
            animation.setAllowEnterTransitionOverlap(false)

            val currentFragment = mActivity.supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment is SharedElementProvider) {
                val sharedElementProvider = currentFragment as SharedElementProvider
                val sharedElement = sharedElementProvider.getSharedElement(animationData)
                val shareElementName = sharedElementProvider.getSharedElementName(animationData)
                animation.addSharedElement(sharedElement, shareElementName)
                val moveTransition = TransitionInflater.from(mActivity).inflateTransition(android.R.transition.move)
                moveTransition.setDuration(600)
                animation.setSharedElementTransition(moveTransition)
            }
            return animation
        } else {
            val enterAnimRes = if (forward) R.anim.slide_in_right else R.anim.slide_in_left
            val exitAnimRes = if (forward) R.anim.slide_out_left else R.anim.slide_out_right
            return SimpleTransitionAnimation(enterAnimRes, exitAnimRes)
        }
    }
}
