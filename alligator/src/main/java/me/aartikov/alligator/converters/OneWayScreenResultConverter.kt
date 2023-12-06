package me.aartikov.alligator.converters

import me.aartikov.alligator.ActivityResult
import me.aartikov.alligator.ScreenResult

/**
 * [ScreenResultConverter] that doesn't require to implement `createActivityResult` method. Should be used for external activities only.
 *
 * @param <ScreenResultT> screen result type
</ScreenResultT> */
abstract class OneWayScreenResultConverter<ScreenResultT : ScreenResult?> :
    ScreenResultConverter<ScreenResultT> {
    override fun createActivityResult(screenResult: ScreenResultT): ActivityResult {
        throw UnsupportedOperationException()
    }
}