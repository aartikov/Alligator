package me.aartikov.alligator.helpers

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import me.aartikov.alligator.animations.DialogAnimation

/**
 * Helper class for showing and hiding a dialog fragment.
 */
class DialogFragmentHelper(private val mFragmentManager: FragmentManager) {
    val dialogFragment: DialogFragment?
        get() {
            val dialogFragment = mFragmentManager.findFragmentByTag(TAG) as DialogFragment?
            return if (dialogFragment == null || dialogFragment.isRemoving) {
                null
            } else {
                dialogFragment
            }
        }
    val isDialogVisible: Boolean
        get() = dialogFragment != null

    fun showDialog(dialogFragment: DialogFragment, animation: DialogAnimation) {
        animation.applyBeforeShowing(dialogFragment)
        dialogFragment.show(mFragmentManager, TAG)
        mFragmentManager.executePendingTransactions()
        animation.applyAfterShowing(dialogFragment)
    }

    fun hideDialog() {
        val dialogFragment = mFragmentManager.findFragmentByTag(TAG) as DialogFragment?
            ?: throw IllegalStateException("Dialog is not visible.")
        dialogFragment.dismiss()
        mFragmentManager.executePendingTransactions()
    }

    companion object {
        private const val TAG = "me.aartikov.alligator.DIALOG_FRAGMENT_HELPER_TAG"
    }
}