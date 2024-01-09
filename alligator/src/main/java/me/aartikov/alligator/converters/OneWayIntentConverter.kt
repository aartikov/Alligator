package me.aartikov.alligator.converters

import android.content.Intent
import me.aartikov.alligator.Screen

/**
 * [IntentConverter] that doesn't require to implement `getScreen` method. Should be used for external activities only.
 *
 * @param <ScreenT> screen type
</ScreenT> */
abstract class OneWayIntentConverter<ScreenT : Screen> : IntentConverter<ScreenT> {
    override fun getScreen(intent: Intent): ScreenT? {
        throw UnsupportedOperationException()
    }
}