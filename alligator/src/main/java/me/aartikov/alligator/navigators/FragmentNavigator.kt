package me.aartikov.alligator.navigators

import androidx.fragment.app.Fragment
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.destinations.FragmentDestination
import me.aartikov.alligator.exceptions.NavigationException

interface FragmentNavigator {
    @Throws(NavigationException::class)
    fun goForward(
        screen: Screen,
        destination: FragmentDestination,
        animationData: AnimationData?
    )

    @Throws(NavigationException::class)
    fun replace(
        screen: Screen,
        destination: FragmentDestination,
        animationData: AnimationData?
    )

    @Throws(NavigationException::class)
    fun reset(
        screen: Screen,
        destination: FragmentDestination,
        animationData: AnimationData?
    )

    fun canGoBack(): Boolean

    @Throws(NavigationException::class)
    fun goBack(
        screenResult: ScreenResult?,
        animationData: AnimationData?
    )

    @Throws(NavigationException::class)
    fun goBackTo(
        screenClass: Class<out Screen>,
        destination: FragmentDestination,
        screenResult: ScreenResult?,
        animationData: AnimationData?
    )

    @Throws(NavigationException::class)
    fun goBackTo(
        screen: Screen,
        destination: FragmentDestination,
        screenResult: ScreenResult?,
        animationData: AnimationData?
    )

    val currentFragment: Fragment?
}