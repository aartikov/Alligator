package me.aartikov.alligator.converters

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.DialogFragment
import me.aartikov.alligator.Screen
import java.io.Serializable

/**
 * Creates a dialog fragment of the given class. It also puts a screen to the fragment's arguments if `ScreenT` is `Serializable` or `Parcelable`.
 *
 * @param <ScreenT> screen type
</ScreenT> */
class DefaultDialogFragmentConverter<ScreenT : Screen?>(
    private val mScreenClass: Class<ScreenT>,
    private val mDialogFragmentClass: Class<out DialogFragment>
) : DialogFragmentConverter<ScreenT> {
    override fun createDialogFragment(screen: ScreenT): DialogFragment {
        return try {
            val dialogFragment = mDialogFragmentClass.newInstance()
            if (screen is Serializable) {
                val arguments = Bundle()
                arguments.putSerializable(KEY_SCREEN, screen as Serializable)
                dialogFragment.arguments = arguments
            } else if (screen is Parcelable) {
                val arguments = Bundle()
                arguments.putParcelable(KEY_SCREEN, screen as Parcelable)
                dialogFragment.arguments = arguments
            }
            dialogFragment
        } catch (e: InstantiationException) {
            throw RuntimeException("Failed to create a dialog fragment", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Failed to create a dialog fragment", e)
        }
    }

    override fun getScreen(dialogFragment: DialogFragment): ScreenT {
        return if (dialogFragment.arguments == null) {
            throw IllegalArgumentException("Fragment has no arguments.")
        } else if (Serializable::class.java.isAssignableFrom(mScreenClass)) {
            checkNotNull(dialogFragment.requireArguments().getSerializable(KEY_SCREEN) as ScreenT?)
        } else if (Parcelable::class.java.isAssignableFrom(mScreenClass)) {
            checkNotNull(
                dialogFragment.requireArguments().getParcelable<Parcelable>(
                    KEY_SCREEN
                ) as ScreenT?
            )
        } else {
            throw IllegalArgumentException("Screen " + mScreenClass.simpleName + " should be Serializable or Parcelable.")
        }
    }

    private fun checkNotNull(screen: ScreenT?): ScreenT {
        requireNotNull(screen) { "Failed to get screen from arguments of fragment." }
        return screen
    }

    companion object {
        private const val KEY_SCREEN = "me.aartikov.alligator.KEY_SCREEN"
    }
}