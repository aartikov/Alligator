package me.aartikov.alligator.commands

import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.exceptions.NavigationException

/**
 * Command implementation for `goBack` method of [me.aartikov.alligator.AndroidNavigator].
 */
class BackCommand(
    private val mScreenResult: ScreenResult?,
    private val mAnimationData: AnimationData?
) : Command {
    @Throws(NavigationException::class)
    override fun execute(navigationContext: NavigationContext): Boolean {
        return if (navigationContext.dialogFragmentNavigator.canGoBack()) {
            navigationContext.dialogFragmentNavigator.goBack(mScreenResult)
            true
        } else if (navigationContext.fragmentNavigator != null && navigationContext.fragmentNavigator!!
                .canGoBack()
        ) {
            navigationContext.fragmentNavigator!!.goBack(mScreenResult, mAnimationData)
            true
        } else if (navigationContext.flowFragmentNavigator != null && navigationContext.flowFragmentNavigator!!
                .canGoBack()
        ) {
            navigationContext.flowFragmentNavigator!!.goBack(mScreenResult, mAnimationData)
            true
        } else {
            navigationContext.activityNavigator.goBack(mScreenResult, mAnimationData)
            false
        }
    }
}