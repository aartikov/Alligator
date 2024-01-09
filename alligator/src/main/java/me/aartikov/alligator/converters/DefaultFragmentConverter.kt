package me.aartikov.alligator.converters

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import me.aartikov.alligator.Screen
import java.io.Serializable

/**
 * Creates a fragment of the given class. It also puts a screen to the fragment's arguments if `ScreenT` is `Serializable` or `Parcelable`.
 *
 * @param <ScreenT> screen type
</ScreenT> */
class DefaultFragmentConverter<ScreenT : Screen>(
    private val mScreenClass: Class<ScreenT>,
    private val mFragmentClass: Class<out Fragment>
) : FragmentConverter<ScreenT> {
    override fun createFragment(screen: ScreenT): Fragment {
        return try {
            val fragment = mFragmentClass.newInstance()
            if (screen is Serializable) {
                val arguments = Bundle()
                arguments.putSerializable(KEY_SCREEN, screen as Serializable)
                fragment.arguments = arguments
            } else if (screen is Parcelable) {
                val arguments = Bundle()
                arguments.putParcelable(KEY_SCREEN, screen as Parcelable)
                fragment.arguments = arguments
            }
            fragment
        } catch (e: InstantiationException) {
            throw RuntimeException("Failed to create a fragment", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Failed to create a fragment", e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getScreen(fragment: Fragment): ScreenT {
        return if (fragment.arguments == null) {
            throw IllegalArgumentException("Fragment has no arguments.")
        } else if (Serializable::class.java.isAssignableFrom(mScreenClass)) {
            checkNotNull(
                fragment.requireArguments().getSerializable(KEY_SCREEN) as? ScreenT
            )
        } else if (Parcelable::class.java.isAssignableFrom(mScreenClass)) {
            checkNotNull(
                fragment.requireArguments().getParcelable<Parcelable>(KEY_SCREEN) as? ScreenT
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