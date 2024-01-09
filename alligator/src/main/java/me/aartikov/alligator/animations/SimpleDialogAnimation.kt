package me.aartikov.alligator.animations

import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment

/**
 * Dialog animation that uses a window animation style resource.
 *
 * @param windowAnimationsStyleRes window animation style resource
 * <pre>`For example:
 * <style name="SlideDialogAnimation">
 * <item name="android:windowEnterAnimation">@anim/slide_in_left</item>
 * <item name="android:windowExitAnimation">@anim/slide_out_right</item>
 * </style>
 * `</pre>
 */
class SimpleDialogAnimation(@param:StyleRes private val mWindowAnimationsStyleRes: Int) :
    DialogAnimation {
    override fun applyBeforeShowing(dialogFragment: DialogFragment) {}
    override fun applyAfterShowing(dialogFragment: DialogFragment) {
        if (dialogFragment.dialog != null && dialogFragment.dialog!!.window != null) {
            dialogFragment.dialog!!.window!!.setWindowAnimations(mWindowAnimationsStyleRes)
        }
    }
}