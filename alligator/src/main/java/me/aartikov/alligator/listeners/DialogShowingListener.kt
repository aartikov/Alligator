package me.aartikov.alligator.listeners

import me.aartikov.alligator.Screen

/**
 * Interface for listening of dialog showing.
 */
interface DialogShowingListener {
    /**
     * Is called when a dialog was shown.
     *
     * @param screenClass class of a screen that represents a dialog.
     */
    fun onDialogShown(screenClass: Class<out Screen>)
}