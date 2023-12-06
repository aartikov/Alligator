package me.aartikov.alligator.navigators

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.providers.DialogAnimationProvider
import me.aartikov.alligator.destinations.DialogFragmentDestination
import me.aartikov.alligator.exceptions.NavigationException
import me.aartikov.alligator.helpers.DialogFragmentHelper
import me.aartikov.alligator.helpers.ScreenResultHelper
import me.aartikov.alligator.listeners.DialogShowingListener
import me.aartikov.alligator.listeners.ScreenResultListener
import me.aartikov.alligator.navigationfactories.NavigationFactory

class DefaultDialogFragmentNavigator(
    fragmentManager: FragmentManager,
    navigationFactory: NavigationFactory,
    dialogShowingListener: DialogShowingListener,
    screenResultListener: ScreenResultListener,
    animationProvider: DialogAnimationProvider
) : DialogFragmentNavigator {
    private val mDialogFragmentHelper: DialogFragmentHelper
    private val mNavigationFactory: NavigationFactory
    private val mScreenResultHelper: ScreenResultHelper
    private val mDialogShowingListener: DialogShowingListener
    private val mScreenResultListener: ScreenResultListener
    private val mAnimationProvider: DialogAnimationProvider

    init {
        mDialogFragmentHelper = DialogFragmentHelper(fragmentManager)
        mNavigationFactory = navigationFactory
        mScreenResultHelper = ScreenResultHelper(mNavigationFactory)
        mDialogShowingListener = dialogShowingListener
        mScreenResultListener = screenResultListener
        mAnimationProvider = animationProvider
    }

    @Throws(NavigationException::class)
    override fun goForward(
        screen: Screen,
        destination: DialogFragmentDestination,
        animationData: AnimationData?
    ) {
        val dialogFragment = destination.createDialogFragment(screen)
        val animation = mAnimationProvider.getAnimation(screen.javaClass, animationData)
        mDialogFragmentHelper.showDialog(dialogFragment, animation)
        mDialogShowingListener.onDialogShown(screen.javaClass)
    }

    @Throws(NavigationException::class)
    override fun replace(
        screen: Screen,
        destination: DialogFragmentDestination,
        animationData: AnimationData?
    ) {
        if (mDialogFragmentHelper.isDialogVisible) {
            mDialogFragmentHelper.hideDialog()
        }
        val dialogFragment = destination.createDialogFragment(screen)
        val animation = mAnimationProvider.getAnimation(screen.javaClass, animationData)
        mDialogFragmentHelper.showDialog(dialogFragment, animation)
        mDialogShowingListener.onDialogShown(screen.javaClass)
    }

    @Throws(NavigationException::class)
    override fun reset(
        screen: Screen,
        destination: DialogFragmentDestination,
        animationData: AnimationData?
    ) {
        while (mDialogFragmentHelper.isDialogVisible) {
            mDialogFragmentHelper.hideDialog()
        }
        val dialogFragment = destination.createDialogFragment(screen)
        val animation = mAnimationProvider.getAnimation(screen.javaClass, animationData)
        mDialogFragmentHelper.showDialog(dialogFragment, animation)
        mDialogShowingListener.onDialogShown(screen.javaClass)
    }

    override fun canGoBack(): Boolean {
        return mDialogFragmentHelper.isDialogVisible
    }

    @Throws(NavigationException::class)
    override fun goBack(screenResult: ScreenResult?) {
        val dialogFragment = mDialogFragmentHelper.dialogFragment
        mDialogFragmentHelper.hideDialog()
        if (dialogFragment != null) {
            mScreenResultHelper.callScreenResultListener(
                dialogFragment,
                screenResult,
                mScreenResultListener
            )
        }
    }

    override val currentDialogFragment: DialogFragment?
        get() = mDialogFragmentHelper.dialogFragment
}