package me.aartikov.alligator.listeners

import me.aartikov.alligator.Screen

/**
 * Interface for listening of screen switching.
 */
fun interface ScreenSwitchingListener {
    /**
     * Is called after a screen has been switched using [ScreenSwitcher].
     *
     * @param screenFrom screen that disappears during a switching or `null` if there was no current screen before switching
     * @param screenTo   screen that appears during a switching
     */
    fun onScreenSwitched(screenFrom: Screen?, screenTo: Screen)
}