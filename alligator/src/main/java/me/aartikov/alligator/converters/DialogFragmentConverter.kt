package me.aartikov.alligator.converters

import androidx.fragment.app.DialogFragment
import me.aartikov.alligator.Screen

/**
 * Converts a screen to a dialog fragment and vice versa.
 *
 * @param <ScreenT> screen type
</ScreenT> */
interface DialogFragmentConverter<ScreenT : Screen?> {
    fun createDialogFragment(screen: ScreenT): DialogFragment
    fun getScreen(fragment: DialogFragment): ScreenT
}