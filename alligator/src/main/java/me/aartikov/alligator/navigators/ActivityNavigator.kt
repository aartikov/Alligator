package me.aartikov.alligator.navigators

import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.destinations.ActivityDestination
import me.aartikov.alligator.exceptions.NavigationException

interface ActivityNavigator {
    @Throws(NavigationException::class)
    fun goForward(
        screen: Screen,
        destination: ActivityDestination,
        animationData: AnimationData?
    )

    @Throws(NavigationException::class)
    fun replace(
        screen: Screen,
        destination: ActivityDestination,
        animationData: AnimationData?
    )

    @Throws(NavigationException::class)
    fun reset(
        screen: Screen,
        destination: ActivityDestination,
        animationData: AnimationData?
    )

    @Throws(NavigationException::class)
    fun goBack(
        screenResult: ScreenResult?,
        animationData: AnimationData?
    )

    @Throws(NavigationException::class)
    fun goBackTo(
        screenClass: Class<out Screen?>,
        destination: ActivityDestination,
        screenResult: ScreenResult?,
        animationData: AnimationData?
    )
}