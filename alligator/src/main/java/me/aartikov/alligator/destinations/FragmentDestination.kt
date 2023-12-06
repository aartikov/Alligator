package me.aartikov.alligator.destinations

import androidx.fragment.app.Fragment
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.converters.FragmentConverter
import me.aartikov.alligator.helpers.ScreenClassHelper

class FragmentDestination(
    private val mScreenClass: Class<out Screen>,
    private val mFragmentConverter: FragmentConverter<out Screen>,
    val screenResultClass: Class<out ScreenResult?>?,
    private val mScreenClassHelper: ScreenClassHelper
) : Destination {
    fun createFragment(screen: Screen): Fragment {
        checkScreenClass(screen.javaClass)
        val fragment = (mFragmentConverter as FragmentConverter<Screen>).createFragment(screen)
        mScreenClassHelper.putScreenClass(fragment, screen.javaClass)
        return fragment
    }

    fun getScreen(fragment: Fragment): Screen {
        return mFragmentConverter.getScreen(fragment)
    }

    private fun checkScreenClass(screenClass: Class<out Screen>) {
        require(mScreenClass.isAssignableFrom(screenClass)) { "Invalid screen class " + screenClass.simpleName + ". Expected " + mScreenClass.simpleName }
    }
}