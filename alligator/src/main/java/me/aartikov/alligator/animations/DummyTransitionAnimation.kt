package me.aartikov.alligator.animations

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

/**
 * Transition animation that leaves a default animation behavior for activities and fragments.
 */
class DummyTransitionAnimation : TransitionAnimation {
    override fun getActivityOptionsBundle(activity: Activity): Bundle? {
        return null
    }

    override fun needDelayActivityFinish(): Boolean {
        return false
    }

    override fun applyBeforeActivityStarted(currentActivity: Activity, intent: Intent) {}
    override fun applyAfterActivityStarted(currentActivity: Activity) {}
    override fun applyBeforeActivityFinished(activity: Activity) {}
    override fun applyAfterActivityFinished(activity: Activity) {}
    override fun applyBeforeFragmentTransactionExecuted(
        transaction: FragmentTransaction,
        enteringFragment: Fragment,
        exitingFragment: Fragment
    ) {
    }

    override fun applyAfterFragmentTransactionExecuted(
        enteringFragment: Fragment,
        exitingFragment: Fragment
    ) {
    }
}