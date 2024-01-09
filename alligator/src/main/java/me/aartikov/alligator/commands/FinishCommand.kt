package me.aartikov.alligator.commands

import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.exceptions.NavigationException

/**
 * Command implementation for `finish` method and `finishWithResult` method of [me.aartikov.alligator.AndroidNavigator].
 */
class FinishCommand(
    private val mScreenResult: ScreenResult?,
    private val mForTopLevel: Boolean,
    private val mAnimationData: AnimationData?
) : Command {

    @Throws(NavigationException::class)
    override fun execute(navigationContext: NavigationContext): Boolean {
        return if (!mForTopLevel && navigationContext.flowFragmentNavigator != null && navigationContext.flowFragmentNavigator.canGoBack()) {
            navigationContext.flowFragmentNavigator.goBack(mScreenResult, mAnimationData)
            true
        } else {
            navigationContext.activityNavigator.goBack(mScreenResult, mAnimationData)
            false
        }
    }
}