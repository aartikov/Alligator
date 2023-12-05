package me.aartikov.alligator.animations

import androidx.fragment.app.DialogFragment

/**
 * Animation that played when a dialog fragment is shown.
 */
interface DialogAnimation {
    /**
     * Is called before a dialog fragment is shown.
     *
     * @param dialogFragment dialog fragment that will be shown
     */
    fun applyBeforeShowing(dialogFragment: DialogFragment)

    /**
     * Is called after a dialog fragment was shown and a related fragment transaction was executed.
     *
     * @param dialogFragment dialog fragment that was shown
     */
    fun applyAfterShowing(dialogFragment: DialogFragment)

    companion object {
        @JvmField
        val DEFAULT: DialogAnimation = DummyDialogAnimation()
    }
}