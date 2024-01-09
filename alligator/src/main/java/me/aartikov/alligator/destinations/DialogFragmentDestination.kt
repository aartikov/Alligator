package me.aartikov.alligator.destinations

import androidx.fragment.app.DialogFragment
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.converters.DialogFragmentConverter
import me.aartikov.alligator.helpers.ScreenClassHelper

class DialogFragmentDestination(
    private val mScreenClass: Class<out Screen>,
    private val mDialogFragmentConverter: DialogFragmentConverter<out Screen>,
    val screenResultClass: Class<out ScreenResult>?,
    private val mScreenClassHelper: ScreenClassHelper
) : Destination {
    fun createDialogFragment(screen: Screen): DialogFragment {
        checkScreenClass(screen.javaClass)
        val dialogFragment =
            (mDialogFragmentConverter as DialogFragmentConverter<Screen>).createDialogFragment(screen)
        mScreenClassHelper.putScreenClass(dialogFragment, screen.javaClass)
        return dialogFragment
    }

    fun getScreen(dialogFragment: DialogFragment): Screen {
        return mDialogFragmentConverter.getScreen(dialogFragment)
    }

    private fun checkScreenClass(screenClass: Class<out Screen>) {
        require(mScreenClass.isAssignableFrom(screenClass)) {
            "Invalid screen class " + screenClass.simpleName + ". Expected " + mScreenClass.simpleName
        }
    }
}