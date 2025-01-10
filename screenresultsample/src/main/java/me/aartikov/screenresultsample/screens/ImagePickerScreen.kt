package me.aartikov.screenresultsample.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import me.aartikov.alligator.ActivityResult
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.converters.OneWayIntentConverter
import me.aartikov.alligator.converters.OneWayScreenResultConverter

class ImagePickerScreen : Screen {

    // Screen result
    class Result(val uri: Uri) : ScreenResult

    // Intent converter. Creates an intent from a screen.
    class Converter : OneWayIntentConverter<ImagePickerScreen>() {

        override fun createIntent(context: Context, screen: ImagePickerScreen): Intent {
            return Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
        }
    }

    // Screen result converter. Creates a screen result from ActivityResult.
    class ResultConverter : OneWayScreenResultConverter<Result>() {

        override fun getScreenResult(activityResult: ActivityResult): Result? {
            val uri = activityResult.dataUri
            return if (uri != null) Result(uri) else null
        }
    }
}
