package me.aartikov.alligator

import android.content.Intent
import me.aartikov.alligator.destinations.ActivityDestination
import me.aartikov.alligator.helpers.ScreenResultHelper
import me.aartikov.alligator.listeners.ScreenResultListener
import me.aartikov.alligator.navigationfactories.NavigationFactory

/**
 * Helper class for handling a screen result.
 */
class ActivityResultHandler internal constructor(private val mNavigationFactory: NavigationFactory) {
    private var mScreenResultListener: ScreenResultListener? = null
    private var mPendingScreenResultPair: ScreenResultPair? = null
    fun setScreenResultListener(screenResultListener: ScreenResultListener) {
        mScreenResultListener = screenResultListener
        handlePendingScreenResult()
    }

    fun resetScreenResultListener() {
        mScreenResultListener = null
    }

    /**
     * Handles activity result. This method should be called from `onActivityResult` of an activity.
     *
     * @param requestCode requestCode passed to `onActivityResult`
     * @param resultCode  resultCode passed to `onActivityResult`
     * @param data        intent passed to `onActivityResult`
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val screenClass = mNavigationFactory.getScreenClass(requestCode)
        if (screenClass != null) {
            val destination = mNavigationFactory.getDestination(screenClass)
            if (destination is ActivityDestination) {
                val screenResult = destination.getScreenResult(ActivityResult(resultCode, data))
                if (mPendingScreenResultPair == null || mPendingScreenResultPair!!.mScreenResult == null) {
                    mPendingScreenResultPair = ScreenResultPair(screenClass, screenResult)
                }
                handlePendingScreenResult()
            }
        }
    }

    /**
     * Handles activity result. This method should be called from `onNewIntent` of an activity.
     *
     * @param intent intent passed to `onNewIntent`
     */
    fun onNewIntent(intent: Intent) {
        val requestCode = intent.getIntExtra(ScreenResultHelper.KEY_REQUEST_CODE, -1)
        if (requestCode != -1) {
            val resultCode = intent.getIntExtra(ScreenResultHelper.KEY_RESULT_CODE, 0)
            onActivityResult(requestCode, resultCode, intent)
        }
    }

    private fun handlePendingScreenResult() {
        if (mScreenResultListener != null && mPendingScreenResultPair != null) {
            mScreenResultListener!!.onScreenResult(
                mPendingScreenResultPair!!.mScreenClass,
                mPendingScreenResultPair!!.mScreenResult
            )
            mPendingScreenResultPair = null
        }
    }

    private inner class ScreenResultPair internal constructor(
        var mScreenClass: Class<out Screen?>,
        var mScreenResult: ScreenResult?
    )
}