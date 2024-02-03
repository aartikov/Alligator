package me.aartikov.alligator.listeners

import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult

/**
 * Interface for screen result handling.
 */
fun interface ScreenResultListener {
    /**
     * Is called when a screen that can return a result has finished.
     *
     * @param screenClass class of a finished screen
     * @param result      returned screen result. Can be null if a screen has finished without no result.
     */
    fun onScreenResult(screenClass: Class<out Screen>, result: ScreenResult?)
}