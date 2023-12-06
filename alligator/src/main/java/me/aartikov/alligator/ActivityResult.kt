package me.aartikov.alligator

import android.app.Activity
import android.content.Intent
import android.net.Uri

/**
 * Wrapper for an activity result. It contains a result code and an intent.
 */
class ActivityResult(val resultCode: Int, val intent: Intent?) {

    val dataUri: Uri?
        get() = intent?.data
    val isOk: Boolean
        get() = resultCode == Activity.RESULT_OK
    val isCanceled: Boolean
        get() = resultCode == Activity.RESULT_CANCELED
}