package me.aartikov.alligator.navigators

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.TransitionType
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.TransitionAnimation
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider
import me.aartikov.alligator.destinations.FragmentDestination
import me.aartikov.alligator.exceptions.NavigationException
import me.aartikov.alligator.exceptions.ScreenNotFoundException
import me.aartikov.alligator.exceptions.ScreenRegistrationException
import me.aartikov.alligator.helpers.FragmentStack
import me.aartikov.alligator.helpers.ScreenResultHelper
import me.aartikov.alligator.listeners.ScreenResultListener
import me.aartikov.alligator.listeners.TransitionListener
import me.aartikov.alligator.navigationfactories.NavigationFactory

class DefaultFragmentNavigator(
    private val mFlowNavigation: Boolean,
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
    navigationFactory: NavigationFactory,
    transitionListener: TransitionListener,
    screenResultListener: ScreenResultListener,
    animationProvider: TransitionAnimationProvider
) : FragmentNavigator {
    private val mFragmentStack: FragmentStack
    private val mNavigationFactory: NavigationFactory
    private val mScreenResultHelper: ScreenResultHelper
    private val mTransitionListener: TransitionListener
    private val mScreenResultListener: ScreenResultListener
    private val mAnimationProvider: TransitionAnimationProvider

    init {
        mFragmentStack = FragmentStack(fragmentManager, containerId)
        mNavigationFactory = navigationFactory
        mScreenResultHelper = ScreenResultHelper(mNavigationFactory)
        mTransitionListener = transitionListener
        mScreenResultListener = screenResultListener
        mAnimationProvider = animationProvider
    }

    @Throws(NavigationException::class)
    override fun goForward(
        screen: Screen,
        destination: FragmentDestination,
        animationData: AnimationData?
    ) {
        val currentFragment = mFragmentStack.currentFragment
        val screenClassFrom =
            if (currentFragment == null) null else mNavigationFactory.getScreenClass(currentFragment)
        val screenClassTo: Class<out Screen> = screen.javaClass
        val fragment = destination.createFragment(screen)
        if (fragment is DialogFragment) {
            throw ScreenRegistrationException("DialogFragment is used as usual Fragment.")
        }
        val animation =
            getAnimation(TransitionType.FORWARD, screenClassFrom, screenClassTo, animationData)
        mFragmentStack.push(fragment, animation)
        callTransitionListener(TransitionType.FORWARD, screenClassFrom, screenClassTo)
    }

    @Throws(NavigationException::class)
    override fun replace(
        screen: Screen,
        destination: FragmentDestination,
        animationData: AnimationData?
    ) {
        val fragment = destination.createFragment(screen)
        val currentFragment = mFragmentStack.currentFragment
        val screenClassFrom =
            if (currentFragment == null) null else mNavigationFactory.getScreenClass(currentFragment)
        val screenClassTo: Class<out Screen> = screen.javaClass
        val animation =
            getAnimation(TransitionType.REPLACE, screenClassFrom, screenClassTo, animationData)
        mFragmentStack.replace(fragment, animation)
        callTransitionListener(TransitionType.REPLACE, screenClassFrom, screenClassTo)
    }

    @Throws(NavigationException::class)
    override fun reset(
        screen: Screen,
        destination: FragmentDestination,
        animationData: AnimationData?
    ) {
        val fragment = destination.createFragment(screen)
        val currentFragment = mFragmentStack.currentFragment
        val screenClassFrom =
            if (currentFragment == null) null else mNavigationFactory.getScreenClass(currentFragment)
        val screenClassTo: Class<out Screen> = screen.javaClass
        val animation =
            getAnimation(TransitionType.RESET, screenClassFrom, screenClassTo, animationData)
        mFragmentStack.reset(fragment, animation)
        callTransitionListener(TransitionType.RESET, screenClassFrom, screenClassTo)
    }

    override fun canGoBack(): Boolean {
        return mFragmentStack.fragmentCount > 1
    }

    @Throws(NavigationException::class)
    override fun goBack(
        screenResult: ScreenResult?,
        animationData: AnimationData?
    ) {
        val fragments = mFragmentStack.fragments
        val currentFragment = fragments[fragments.size - 1]
        val previousFragment = fragments[fragments.size - 2]
        val screenClassFrom = mNavigationFactory.getScreenClass(currentFragment)
        val screenClassTo = mNavigationFactory.getScreenClass(previousFragment)
        val animation =
            getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, animationData)
        mFragmentStack.pop(animation)
        callTransitionListener(TransitionType.BACK, screenClassFrom, screenClassTo)
        mScreenResultHelper.callScreenResultListener(
            currentFragment,
            screenResult,
            mScreenResultListener
        )
    }

    @Throws(NavigationException::class)
    override fun goBackTo(
        screenClass: Class<out Screen>,
        destination: FragmentDestination,
        screenResult: ScreenResult?,
        animationData: AnimationData?
    ) {
        val fragments = mFragmentStack.fragments
        var requiredFragment: Fragment? = null
        var toPrevious = false
        for (i in fragments.indices.reversed()) {
            if (screenClass == mNavigationFactory.getScreenClass(fragments[i])) {
                requiredFragment = fragments[i]
                toPrevious = i == fragments.size - 2
                break
            }
        }
        if (requiredFragment == null) {
            throw ScreenNotFoundException(screenClass)
        }
        val currentFragment = fragments[fragments.size - 1]
        val screenClassFrom = mNavigationFactory.getScreenClass(currentFragment)
        val animation =
            getAnimation(TransitionType.BACK, screenClassFrom, screenClass, animationData)
        mFragmentStack.popUntil(requiredFragment, animation)
        callTransitionListener(TransitionType.BACK, screenClassFrom, screenClass)
        if (screenResult != null || toPrevious) {
            mScreenResultHelper.callScreenResultListener(
                currentFragment,
                screenResult,
                mScreenResultListener
            )
        }
    }

    @Throws(NavigationException::class)
    override fun goBackTo(
        screen: Screen,
        destination: FragmentDestination,
        screenResult: ScreenResult?,
        animationData: AnimationData?
    ) {
        val fragments = mFragmentStack.fragments
        var requiredFragment: Fragment? = null
        var toPrevious = false
        for (i in fragments.indices.reversed()) {
            if (screen === destination.getScreen(fragments[i])) {
                requiredFragment = fragments[i]
                toPrevious = i == fragments.size - 2
                break
            }
        }
        if (requiredFragment == null) {
            throw ScreenNotFoundException(screen.javaClass)
        }
        val currentFragment = fragments[fragments.size - 1]
        val screenClassFrom = mNavigationFactory.getScreenClass(currentFragment)
        val animation =
            getAnimation(TransitionType.BACK, screenClassFrom, screen.javaClass, animationData)
        mFragmentStack.popUntil(requiredFragment, animation)
        callTransitionListener(TransitionType.BACK, screenClassFrom, screen.javaClass)
        if (screenResult != null || toPrevious) {
            mScreenResultHelper.callScreenResultListener(
                currentFragment,
                screenResult,
                mScreenResultListener
            )
        }
    }

    override val currentFragment: Fragment?
        get() = mFragmentStack.currentFragment

    private fun getAnimation(
        transitionType: TransitionType,
        screenClassFrom: Class<out Screen>?,
        screenClassTo: Class<out Screen>?,
        animationData: AnimationData?
    ): TransitionAnimation {
        return if (screenClassFrom == null || screenClassTo == null) {
            TransitionAnimation.DEFAULT
        } else {
            val destinationType =
                if (mFlowNavigation) DestinationType.FLOW_FRAGMENT else DestinationType.FRAGMENT
            mAnimationProvider.getAnimation(
                transitionType,
                destinationType,
                screenClassFrom,
                screenClassTo,
                animationData
            )
        }
    }

    private fun callTransitionListener(
        transitionType: TransitionType,
        screenClassFrom: Class<out Screen>?,
        screenClassTo: Class<out Screen>?
    ) {
        val destinationType =
            if (mFlowNavigation) DestinationType.FLOW_FRAGMENT else DestinationType.FRAGMENT
        mTransitionListener.onScreenTransition(
            transitionType,
            destinationType,
            screenClassFrom,
            screenClassTo
        )
    }
}