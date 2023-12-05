package me.aartikov.alligator.screenswitchers

import me.aartikov.alligator.Screen
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.exceptions.NavigationException
import me.aartikov.alligator.listeners.ScreenSwitchingListener

/**
 * Object for switching between several screens without theirs recreation.
 */
interface ScreenSwitcher {
    /**
     * Switches to a given screen. Implementation must call the passed screen switching listener properly.
     *
     * @param screen        screen
     * @param listener      screen switching listener
     * @param animationData animation data for an additional animation configuring
     * @throws me.aartikov.alligator.exceptions.NavigationException on fail
     */
    @Throws(NavigationException::class)
    fun switchTo(screen: Screen?, listener: ScreenSwitchingListener?, animationData: AnimationData?)
}