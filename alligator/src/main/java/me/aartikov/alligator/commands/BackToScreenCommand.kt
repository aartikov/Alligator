package me.aartikov.alligator.commands

import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.destinations.ActivityDestination
import me.aartikov.alligator.destinations.DialogFragmentDestination
import me.aartikov.alligator.destinations.FragmentDestination
import me.aartikov.alligator.exceptions.NavigationException
import me.aartikov.alligator.exceptions.NotSupportedOperationException
import me.aartikov.alligator.navigators.ActivityNavigator
import me.aartikov.alligator.navigators.DialogFragmentNavigator
import me.aartikov.alligator.navigators.FragmentNavigator

/**
 * Command implementation for `goBackTo` method of [me.aartikov.alligator.AndroidNavigator].
 */
class BackToScreenCommand(
    private val mScreen: Screen,
    private val mScreenResult: ScreenResult?,
    private val mAnimationData: AnimationData?
) : BaseCommand(mScreen.javaClass) {

    @Throws(NavigationException::class)
    override fun executeForActivity(
        destination: ActivityDestination,
        activityNavigator: ActivityNavigator
    ) {
        throw NotSupportedOperationException("BackToScreen command is not supported for activity.")
    }

    @Throws(NavigationException::class)
    override fun executeForFragment(
        destination: FragmentDestination,
        fragmentNavigator: FragmentNavigator
    ) {
        fragmentNavigator.goBackTo(mScreen, destination, mScreenResult, mAnimationData)
    }

    @Throws(NavigationException::class)
    override fun executeForDialogFragment(
        destination: DialogFragmentDestination,
        dialogFragmentNavigator: DialogFragmentNavigator
    ) {
        throw NotSupportedOperationException("BackToScreen command is not supported for dialog fragments.")
    }
}