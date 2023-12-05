package me.aartikov.alligator.converters

import android.content.Context
import android.content.Intent
import me.aartikov.alligator.Screen

/**
 * Converts a screen to an activity intent and vice versa.
 *
 * @param <ScreenT> screen type
</ScreenT> */
interface IntentConverter<ScreenT : Screen?> {
    fun createIntent(context: Context, screen: ScreenT): Intent
    fun getScreen(intent: Intent): ScreenT?
}