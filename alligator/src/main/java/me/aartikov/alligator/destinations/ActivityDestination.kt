package me.aartikov.alligator.destinations

import android.app.Activity
import android.content.Context
import android.content.Intent
import me.aartikov.alligator.ActivityResult
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.converters.IntentConverter
import me.aartikov.alligator.converters.ScreenResultConverter
import me.aartikov.alligator.helpers.ScreenClassHelper

class ActivityDestination(
    private val mScreenClass: Class<out Screen>,
    private val mActivityClass: Class<out Activity>?,
    private val mIntentConverter: IntentConverter<out Screen>,
    val screenResultClass: Class<out ScreenResult>?,
    private val mScreenResultConverter: ScreenResultConverter<out ScreenResult>?,
    val requestCode: Int,
    private val mScreenClassHelper: ScreenClassHelper
) : Destination {

    constructor(
        screenClass: Class<out Screen>,
        activityClass: Class<out Activity>?,
        intentConverter: IntentConverter<out Screen>,
        screenClassHelper: ScreenClassHelper
    ) : this(screenClass, activityClass, intentConverter, null, null, -1, screenClassHelper)

    fun createIntent(
        context: Context,
        screen: Screen,
        previousScreenClass: Class<out Screen>?
    ): Intent {
        checkScreenClass(screen.javaClass)
        val intent = (mIntentConverter as IntentConverter<Screen>).createIntent(context, screen)
        mScreenClassHelper.putScreenClass(intent, screen.javaClass)
        if (previousScreenClass != null) {
            mScreenClassHelper.putPreviousScreenClass(intent, previousScreenClass)
        }
        return intent
    }

    fun createEmptyIntent(context: Context, screenClass: Class<out Screen>): Intent? {
        if (mActivityClass == null) {
            return null
        }
        val intent = Intent(context, mActivityClass)
        mScreenClassHelper.putScreenClass(intent, screenClass)
        return intent
    }

    fun createActivityResult(screenResult: ScreenResult): ActivityResult {
        checkScreenResultClass(screenResult.javaClass)
        if (mScreenResultConverter == null) {
            throw RuntimeException("mScreenResultConverter is null")
        }
        return (mScreenResultConverter as ScreenResultConverter<ScreenResult>).createActivityResult(
            screenResult
        )
    }

    fun getScreen(activity: Activity): Screen? {
        return mIntentConverter.getScreen(activity.intent)
    }

    fun getScreenResult(activityResult: ActivityResult): ScreenResult? {
        if (mScreenResultConverter == null) {
            throw RuntimeException("mScreenResultConverter is null")
        }
        return (mScreenResultConverter as ScreenResultConverter<ScreenResult>).getScreenResult(activityResult)
    }

    private fun checkScreenClass(screenClass: Class<out Screen>) {
        require(mScreenClass.isAssignableFrom(screenClass)) {
            "Invalid screen class " + screenClass.simpleName + ". Expected " + mScreenClass.simpleName
        }
    }

    private fun checkScreenResultClass(screenResultClass: Class<out ScreenResult>) {
        if (this.screenResultClass == null) {
            throw RuntimeException("mScreenResultClass is null")
        }
        require(this.screenResultClass.isAssignableFrom(screenResultClass)) {
            "Invalid screen result class " + screenResultClass.canonicalName + ". Expected " + this.screenResultClass.canonicalName
        }
    }
}