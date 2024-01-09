package me.aartikov.alligator.navigators

import androidx.fragment.app.DialogFragment
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.destinations.DialogFragmentDestination
import me.aartikov.alligator.exceptions.NavigationException

interface DialogFragmentNavigator {
    @Throws(NavigationException::class)
    fun goForward(
        screen: Screen,
        destination: DialogFragmentDestination,
        animationData: AnimationData?
    )

    @Throws(NavigationException::class)
    fun replace(
        screen: Screen,
        destination: DialogFragmentDestination,
        animationData: AnimationData?
    )

    @Throws(NavigationException::class)
    fun reset(
        screen: Screen,
        destination: DialogFragmentDestination,
        animationData: AnimationData?
    )

    fun canGoBack(): Boolean

    @Throws(NavigationException::class)
    fun goBack(screenResult: ScreenResult?)
    val currentDialogFragment: DialogFragment?
}