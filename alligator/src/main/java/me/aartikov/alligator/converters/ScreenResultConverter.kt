package me.aartikov.alligator.converters

import me.aartikov.alligator.ActivityResult
import me.aartikov.alligator.ScreenResult

/**
 * Converts [ScreenResult] to [ActivityResult] and vice versa.
 *
 * @param <ScreenResultT> screen result type
</ScreenResultT> */
interface ScreenResultConverter<ScreenResultT : ScreenResult> {
    fun createActivityResult(screenResult: ScreenResultT): ActivityResult
    fun getScreenResult(activityResult: ActivityResult): ScreenResultT?
}