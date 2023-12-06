package me.aartikov.alligator.helpers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import me.aartikov.alligator.animations.TransitionAnimation

/**
 * Helper class for starting and finishing an activity with animation.
 */
class ActivityHelper(private val mActivity: AppCompatActivity) {
    fun resolve(intent: Intent): Boolean {
        return intent.resolveActivity(mActivity.packageManager) != null
    }

    fun start(intent: Intent, animation: TransitionAnimation) {
        val optionsBundle = animation.getActivityOptionsBundle(mActivity)
        animation.applyBeforeActivityStarted(mActivity, intent)
        ActivityCompat.startActivity(mActivity, intent, optionsBundle)
        animation.applyAfterActivityStarted(mActivity)
    }

    fun startForResult(intent: Intent, requestCode: Int, animation: TransitionAnimation) {
        val optionsBundle = animation.getActivityOptionsBundle(mActivity)
        animation.applyBeforeActivityStarted(mActivity, intent)
        ActivityCompat.startActivityForResult(mActivity, intent, requestCode, optionsBundle)
        animation.applyAfterActivityStarted(mActivity)
    }

    fun finish(animation: TransitionAnimation) {
        animation.applyBeforeActivityFinished(mActivity)
        if (animation.needDelayActivityFinish()) {
            mActivity.supportFinishAfterTransition()
        } else {
            mActivity.finish()
        }
        animation.applyAfterActivityFinished(mActivity)
    }
}