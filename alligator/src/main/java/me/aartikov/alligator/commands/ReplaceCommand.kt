package me.aartikov.alligator.commands

import me.aartikov.alligator.Screen
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.destinations.ActivityDestination
import me.aartikov.alligator.destinations.DialogFragmentDestination
import me.aartikov.alligator.destinations.FragmentDestination
import me.aartikov.alligator.exceptions.NavigationException
import me.aartikov.alligator.navigators.ActivityNavigator
import me.aartikov.alligator.navigators.DialogFragmentNavigator
import me.aartikov.alligator.navigators.FragmentNavigator

/**
 * Command implementation for `replace` method of [me.aartikov.alligator.AndroidNavigator].
 */
class ReplaceCommand(
    private val mScreen: Screen,
    private val mAnimationData: AnimationData?
) : BaseCommand(mScreen.javaClass) {

    @Throws(NavigationException::class)
    override fun executeForActivity(
        destination: ActivityDestination,
        activityNavigator: ActivityNavigator
    ) {
        activityNavigator.replace(mScreen, destination, mAnimationData)
    }

    @Throws(NavigationException::class)
    override fun executeForFragment(
        destination: FragmentDestination,
        fragmentNavigator: FragmentNavigator
    ) {
        fragmentNavigator.replace(mScreen, destination, mAnimationData)
    }

    @Throws(NavigationException::class)
    override fun executeForDialogFragment(
        destination: DialogFragmentDestination,
        dialogFragmentNavigator: DialogFragmentNavigator
    ) {
        dialogFragmentNavigator.replace(mScreen, destination, mAnimationData)
    }
}