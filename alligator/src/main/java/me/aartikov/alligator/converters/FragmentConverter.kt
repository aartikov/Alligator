package me.aartikov.alligator.converters

import androidx.fragment.app.Fragment
import me.aartikov.alligator.Screen

/**
 * Converts a screen to a fragment and vice versa.
 *
 * @param <ScreenT> screen type
 */
interface FragmentConverter<ScreenT : Screen> {
    fun createFragment(screen: ScreenT): Fragment
    fun getScreen(fragment: Fragment): ScreenT
}