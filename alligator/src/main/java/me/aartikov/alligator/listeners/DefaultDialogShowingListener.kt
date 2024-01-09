package me.aartikov.alligator.listeners

import me.aartikov.alligator.Screen

/**
 * Dialog showing listener that does nothing.
 */
class DefaultDialogShowingListener : DialogShowingListener {
    override fun onDialogShown(screenClass: Class<out Screen>) {}
}