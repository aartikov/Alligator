package me.aartikov.alligator.animations

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

/**
 * Transition animation that uses anim resources.
 *
 * @param enterAnimation animation resource that will be used for an appearing activity/fragment
 * @param exitAnimation  animation resource that will be used for a disappearing activity/fragment
 */
class SimpleTransitionAnimation(
    @param:AnimRes val enterAnimation: Int,
    @param:AnimRes val exitAnimation: Int
) : TransitionAnimation {

    override fun getActivityOptionsBundle(activity: Activity): Bundle? {
        return null
    }

    override fun needDelayActivityFinish(): Boolean {
        return false
    }

    override fun applyBeforeActivityStarted(currentActivity: Activity, intent: Intent) {}
    override fun applyAfterActivityStarted(currentActivity: Activity) {
        currentActivity.overridePendingTransition(enterAnimation, exitAnimation)
    }

    override fun applyBeforeActivityFinished(activity: Activity) {}
    override fun applyAfterActivityFinished(activity: Activity) {
        activity.overridePendingTransition(enterAnimation, exitAnimation)
    }

    override fun applyBeforeFragmentTransactionExecuted(
        transaction: FragmentTransaction,
        enteringFragment: Fragment,
        exitingFragment: Fragment
    ) {
        transaction.setCustomAnimations(enterAnimation, exitAnimation)
    }

    override fun applyAfterFragmentTransactionExecuted(
        enteringFragment: Fragment,
        exitingFragment: Fragment
    ) {
    }
}