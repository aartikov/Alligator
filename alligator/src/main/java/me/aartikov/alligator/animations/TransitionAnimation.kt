package me.aartikov.alligator.animations

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

/**
 * Animation that played during a transition from one screen to another.
 */
interface TransitionAnimation {
    /**
     * Is called before starting of an activity. Used to pass an options bundle to a started activity.
     *
     * @param activity current activity
     * @return options bundle for activity starting. Can be `null` if there are no options needed.
     */
    fun getActivityOptionsBundle(activity: Activity): Bundle?

    /**
     * Is called before finishing of an activity. Checks if there is need to delay an activity finish.
     *
     *
     * An activity will finish using `supportFinishAfterTransition` if this method returns `true`, otherwise - using `finish`.
     *
     * @return true if an activity finish should be delayed
     */
    fun needDelayActivityFinish(): Boolean

    /**
     * Is called before starting of an activity.
     *
     * @param currentActivity activity that will start another activity
     * @param intent          intent that will be used to start an activity
     */
    fun applyBeforeActivityStarted(currentActivity: Activity, intent: Intent)

    /**
     * Is called after starting of an activity.
     *
     * @param currentActivity activity that started another activity
     */
    fun applyAfterActivityStarted(currentActivity: Activity)

    /**
     * Is called before finishing of an activity.
     *
     * @param activity that will finish
     */
    fun applyBeforeActivityFinished(activity: Activity)

    /**
     * Is called after finishing of an activity.
     *
     * @param activity finished activity
     */
    fun applyAfterActivityFinished(activity: Activity)

    /**
     * Is called before a fragment transaction executed.
     *
     * @param transaction      fragment transaction
     * @param enteringFragment fragment that will be added/attached during the transaction
     * @param exitingFragment  fragment that will be removed/detached  during the transaction
     */
    fun applyBeforeFragmentTransactionExecuted(
        transaction: FragmentTransaction,
        enteringFragment: Fragment,
        exitingFragment: Fragment
    )

    /**
     * Is called after a fragment transaction executed.
     *
     * @param enteringFragment fragment that will be added/attached during the transaction
     * @param exitingFragment  fragment that will be removed/detached  during the transaction
     */
    fun applyAfterFragmentTransactionExecuted(enteringFragment: Fragment, exitingFragment: Fragment)

    companion object {
        @JvmField
        val DEFAULT: TransitionAnimation = DummyTransitionAnimation()
    }
}