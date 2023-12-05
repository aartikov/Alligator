package me.aartikov.alligator.animations

import androidx.fragment.app.DialogFragment

/**
 * Dialog animation that leaves a default animation behavior for dialog fragments.
 */
class DummyDialogAnimation : DialogAnimation {
    override fun applyBeforeShowing(dialogFragment: DialogFragment) {}
    override fun applyAfterShowing(dialogFragment: DialogFragment) {}
}