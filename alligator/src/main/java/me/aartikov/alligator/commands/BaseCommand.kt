package me.aartikov.alligator.commands

import me.aartikov.alligator.FlowScreen
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.Screen
import me.aartikov.alligator.destinations.ActivityDestination
import me.aartikov.alligator.destinations.DialogFragmentDestination
import me.aartikov.alligator.destinations.FragmentDestination
import me.aartikov.alligator.exceptions.MissingFlowFragmentNavigatorException
import me.aartikov.alligator.exceptions.MissingFragmentNavigatorException
import me.aartikov.alligator.exceptions.NavigationException
import me.aartikov.alligator.exceptions.ScreenRegistrationException
import me.aartikov.alligator.navigators.ActivityNavigator
import me.aartikov.alligator.navigators.DialogFragmentNavigator
import me.aartikov.alligator.navigators.FragmentNavigator

abstract class BaseCommand(private val mScreenClass: Class<out Screen?>) : Command {

    @Throws(NavigationException::class)
    protected abstract fun executeForActivity(
        destination: ActivityDestination,
        activityNavigator: ActivityNavigator
    )

    @Throws(NavigationException::class)
    protected abstract fun executeForFragment(
        destination: FragmentDestination,
        fragmentNavigator: FragmentNavigator
    )

    @Throws(NavigationException::class)
    protected abstract fun executeForDialogFragment(
        destination: DialogFragmentDestination,
        dialogFragmentNavigator: DialogFragmentNavigator
    )

    @Throws(NavigationException::class)
    override fun execute(navigationContext: NavigationContext): Boolean {
        val destination = navigationContext.navigationFactory.getDestination(mScreenClass)
            ?: throw ScreenRegistrationException("Screen " + mScreenClass.simpleName + " is not registered.")
        return if (destination is ActivityDestination) {
            executeForActivity(destination, navigationContext.activityNavigator)
            false
        } else if (destination is FragmentDestination) {
            if (FlowScreen::class.java.isAssignableFrom(mScreenClass)) {
                if (navigationContext.flowFragmentNavigator == null) {
                    throw MissingFlowFragmentNavigatorException()
                }
                executeForFragment(
                    destination,
                    navigationContext.flowFragmentNavigator!!
                )
            } else {
                if (navigationContext.fragmentNavigator == null) {
                    throw MissingFragmentNavigatorException()
                }
                executeForFragment(
                    destination,
                    navigationContext.fragmentNavigator!!
                )
            }
            true
        } else if (destination is DialogFragmentDestination) {
            executeForDialogFragment(
                destination,
                navigationContext.dialogFragmentNavigator
            )
            true
        } else {
            throw UnsupportedOperationException("Unsupported destination type $destination")
        }
    }
}