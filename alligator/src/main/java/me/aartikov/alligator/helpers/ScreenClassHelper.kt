package me.aartikov.alligator.helpers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import me.aartikov.alligator.Screen

/**
 * Helper class for storing screen class information in activities and fragments.
 */
class ScreenClassHelper {
    private val mActivityMap: MutableMap<Class<out Activity>, Class<out Screen>>
    = HashMap() // this map is used when there are no screen class information in an activity intent

    private val mRequestCodeMap: MutableMap<Int, Class<out Screen>> = LinkedHashMap()

    fun putScreenClass(intent: Intent, screenClass: Class<out Screen>) {
        intent.putExtra(KEY_SCREEN_CLASS_NAME, screenClass.name)
    }

    @Suppress("UNCHECKED_CAST")
    fun getScreenClass(activity: Activity): Class<out Screen>? {
        val className = activity.intent.getStringExtra(KEY_SCREEN_CLASS_NAME)
        val screenClass: Class<out Screen>? = getClassByName(className) as? Class<out Screen>
        return screenClass ?: mActivityMap[activity.javaClass]
    }

    fun putScreenClass(fragment: Fragment, screenClass: Class<out Screen>) {
        var arguments = fragment.arguments
        if (arguments == null) {
            arguments = Bundle()
            fragment.arguments = arguments
        }
        arguments.putString(KEY_SCREEN_CLASS_NAME, screenClass.name)
    }

    @Suppress("UNCHECKED_CAST")
    fun getScreenClass(fragment: Fragment): Class<out Screen>? {
        if (fragment.arguments == null) {
            return null
        }
        val className = fragment.requireArguments().getString(KEY_SCREEN_CLASS_NAME)
        return getClassByName(className) as? Class<out Screen>
    }

    fun putPreviousScreenClass(intent: Intent, screenClass: Class<out Screen>) {
        intent.putExtra(KEY_PREVIOUS_SCREEN_CLASS_NAME, screenClass.name)
    }

    @Suppress("UNCHECKED_CAST")
    fun getPreviousScreenClass(activity: Activity): Class<out Screen>? {
        val className = activity.intent.getStringExtra(KEY_PREVIOUS_SCREEN_CLASS_NAME)
        return getClassByName(className) as? Class<out Screen>
    }

    fun getScreenClass(requestCode: Int): Class<out Screen>? {
        return mRequestCodeMap[requestCode]
    }

    fun addActivityClass(activityClass: Class<out Activity>, screenClass: Class<out Screen>) {
        if (!mActivityMap.containsKey(activityClass)) {
            mActivityMap[activityClass] = screenClass
        }
    }

    fun addRequestCode(requestCode: Int, screenClass: Class<out Screen>) {
        if (!mRequestCodeMap.containsKey(requestCode)) {
            mRequestCodeMap[requestCode] = screenClass
        }
    }

    companion object {
        private const val KEY_SCREEN_CLASS_NAME = "me.aartikov.alligator.KEY_SCREEN_CLASS_NAME"
        private const val KEY_PREVIOUS_SCREEN_CLASS_NAME = "me.aartikov.alligator.KEY_PREVIOUS_SCREEN_CLASS_NAME"

        private fun getClassByName(className: String?): Class<*>? {
            return if (className.isNullOrEmpty()) {
                null
            } else try {
                Class.forName(className)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                null
            }
        }
    }
}