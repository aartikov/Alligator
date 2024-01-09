package me.aartikov.alligator.helpers

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.destinations.ActivityDestination
import me.aartikov.alligator.destinations.DialogFragmentDestination
import me.aartikov.alligator.destinations.FragmentDestination
import me.aartikov.alligator.exceptions.InvalidScreenResultException
import me.aartikov.alligator.exceptions.NavigationException
import me.aartikov.alligator.exceptions.ScreenRegistrationException
import me.aartikov.alligator.listeners.ScreenResultListener
import me.aartikov.alligator.navigationfactories.NavigationFactory

/**
 * Helps to return a screen result from activities and fragments.
 */
class ScreenResultHelper(private val mNavigationFactory: NavigationFactory) {

    @Throws(NavigationException::class)
    fun setActivityResult(activity: Activity, screenResult: ScreenResult) {
        val activityDestination = getAndValidateActivityDestination(activity, screenResult)
        val activityResult = activityDestination.createActivityResult(screenResult)
        activity.setResult(activityResult.resultCode, activityResult.intent)
    }

    @Throws(NavigationException::class)
    fun setResultToIntent(intent: Intent, activity: Activity, screenResult: ScreenResult) {
        val activityDestination = getAndValidateActivityDestination(activity, screenResult)
        val activityResult = activityDestination.createActivityResult(screenResult)
        intent.putExtra(KEY_REQUEST_CODE, activityDestination.requestCode)
        intent.putExtra(KEY_RESULT_CODE, activityResult.resultCode)
        val resultIntent = activityResult.intent
        if (resultIntent != null) {
            intent.putExtras(resultIntent)
        }
    }

    @Throws(NavigationException::class)
    private fun getAndValidateActivityDestination(
        activity: Activity,
        screenResult: ScreenResult
    ): ActivityDestination {
        val screenClass = mNavigationFactory.getScreenClass(activity)
            ?: throw ScreenRegistrationException("Failed to get a screen class for " + activity.javaClass.simpleName)
        val activityDestination =
            mNavigationFactory.getDestination(screenClass) as? ActivityDestination
                ?: throw ScreenRegistrationException("Failed to get a destination for " + screenClass.simpleName)
        if (activityDestination.screenResultClass == null) {
            throw InvalidScreenResultException("Screen " + screenClass.simpleName + " can't return a result.")
        }
        val supportedScreenResultClass = activityDestination.screenResultClass
        if (!supportedScreenResultClass.isAssignableFrom(screenResult.javaClass)) {
            throw InvalidScreenResultException(
                "Screen " + screenClass.simpleName + " can't return a result of class " + screenResult.javaClass.canonicalName +
                            ". It returns a result of class " + supportedScreenResultClass.canonicalName
            )
        }
        return activityDestination
    }

    @Throws(NavigationException::class)
    fun callScreenResultListener(
        fragment: Fragment,
        screenResult: ScreenResult?,
        screenResultListener: ScreenResultListener
    ) {
        val screenClass = mNavigationFactory.getScreenClass(fragment)
            ?: throw ScreenRegistrationException("Failed to get a screen class for " + fragment.javaClass.simpleName)
        val fragmentDestination =
            mNavigationFactory.getDestination(screenClass) as? FragmentDestination
                ?: throw ScreenRegistrationException("Failed to get a destination for " + screenClass.simpleName)
        val supportedScreenResultClass = fragmentDestination.screenResultClass
        if (supportedScreenResultClass == null) {
            if (screenResult == null) {
                return
            } else {
                throw InvalidScreenResultException("Screen " + screenClass.simpleName + " can't return a result.")
            }
        }

        if (screenResult != null && !supportedScreenResultClass.isAssignableFrom(screenResult.javaClass)) {
            throw InvalidScreenResultException(
                "Screen " + screenClass.simpleName + " can't return a result of class " + screenResult.javaClass.canonicalName +
                            ". It returns a result of class " + supportedScreenResultClass.canonicalName
            )
        }
        screenResultListener.onScreenResult(screenClass, screenResult)
    }

    @Throws(NavigationException::class)
    fun callScreenResultListener(
        dialogFragment: DialogFragment,
        screenResult: ScreenResult?,
        screenResultListener: ScreenResultListener
    ) {
        val screenClass = mNavigationFactory.getScreenClass(dialogFragment)
            ?: throw ScreenRegistrationException("Failed to get a screen class for " + dialogFragment.javaClass.simpleName)

        val dialogFragmentDestination = mNavigationFactory.getDestination(screenClass) as DialogFragmentDestination?
                ?: throw ScreenRegistrationException("Failed to get a destination for " + screenClass.simpleName)

        val supportedScreenResultClass = dialogFragmentDestination.screenResultClass
        if (supportedScreenResultClass == null) {
            if (screenResult == null) {
                return
            } else {
                throw InvalidScreenResultException("Screen " + screenClass.simpleName + " can't return a result.")
            }
        }

        if (screenResult != null && !supportedScreenResultClass.isAssignableFrom(screenResult.javaClass)) {
            throw InvalidScreenResultException(
                "Screen " + screenClass.simpleName + " can't return a result of class " + screenResult.javaClass.canonicalName +
                            ". It returns a result of class " + supportedScreenResultClass.canonicalName
            )
        }

        screenResultListener.onScreenResult(screenClass, screenResult)
    }

    companion object {
        const val KEY_REQUEST_CODE = "me.aartikov.alligator.KEY_REQUEST_CODE"
        const val KEY_RESULT_CODE = "me.aartikov.alligator.KEY_RESULT_CODE"
    }
}