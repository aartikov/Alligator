package me.aartikov.alligator.commands

import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.Screen
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.exceptions.MissingScreenSwitcherException
import me.aartikov.alligator.exceptions.NavigationException

/**
 * Command implementation for `switchTo` method of [me.aartikov.alligator.AndroidNavigator].
 */
class SwitchToCommand(
    private val mScreen: Screen,
    private val mAnimationData: AnimationData?
) : Command {

    @Throws(NavigationException::class)
    override fun execute(navigationContext: NavigationContext): Boolean {
        val screenSwitcher = navigationContext.screenSwitcher
            ?: throw MissingScreenSwitcherException("ScreenSwitcher is not set.")
        screenSwitcher.switchTo(mScreen, navigationContext.screenSwitchingListener, mAnimationData)
        return true
    }
}