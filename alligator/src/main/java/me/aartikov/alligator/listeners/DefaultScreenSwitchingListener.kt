package me.aartikov.alligator.listeners

import me.aartikov.alligator.Screen

/**
 * Screen switching listener that does nothing.
 */
class DefaultScreenSwitchingListener : ScreenSwitchingListener {
    override fun onScreenSwitched(screenFrom: Screen?, screenTo: Screen) {}
}