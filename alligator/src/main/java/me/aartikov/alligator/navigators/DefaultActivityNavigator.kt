package me.aartikov.alligator.navigators

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.TransitionType
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.animations.TransitionAnimation
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider
import me.aartikov.alligator.destinations.ActivityDestination
import me.aartikov.alligator.exceptions.ActivityResolvingException
import me.aartikov.alligator.exceptions.NavigationException
import me.aartikov.alligator.exceptions.ScreenRegistrationException
import me.aartikov.alligator.helpers.ActivityHelper
import me.aartikov.alligator.helpers.ScreenResultHelper
import me.aartikov.alligator.listeners.TransitionListener
import me.aartikov.alligator.navigationfactories.NavigationFactory

class DefaultActivityNavigator(
    private val mActivity: AppCompatActivity,
    private val mNavigationFactory: NavigationFactory,
    transitionListener: TransitionListener,
    animationProvider: TransitionAnimationProvider
) : ActivityNavigator {
    private val mActivityHelper: ActivityHelper = ActivityHelper(mActivity)
    private val mScreenResultHelper: ScreenResultHelper = ScreenResultHelper(mNavigationFactory)
    private val mTransitionListener: TransitionListener
    private val mAnimationProvider: TransitionAnimationProvider

    init {
        mTransitionListener = transitionListener
        mAnimationProvider = animationProvider
    }

    @Throws(NavigationException::class)
    override fun goForward(
        screen: Screen,
        destination: ActivityDestination,
        animationData: AnimationData?
    ) {
        val screenClassFrom = mNavigationFactory.getScreenClass(mActivity)
        val screenClassTo: Class<out Screen?> = screen.javaClass
        val intent = destination.createIntent(mActivity, screen, screenClassFrom)
        if (!mActivityHelper.resolve(intent)) {
            throw ActivityResolvingException(screen)
        }
        val animation =
            getAnimation(TransitionType.FORWARD, screenClassFrom, screenClassTo, animationData)
        if (destination.screenResultClass != null) {
            mActivityHelper.startForResult(intent, destination.requestCode, animation)
        } else {
            mActivityHelper.start(intent, animation)
        }
        callTransitionListener(TransitionType.FORWARD, screenClassFrom, screenClassTo)
    }

    @Throws(NavigationException::class)
    override fun replace(
        screen: Screen,
        destination: ActivityDestination,
        animationData: AnimationData?
    ) {
        val previousScreenClass = mNavigationFactory.getPreviousScreenClass(mActivity)
        val intent = destination.createIntent(mActivity, screen, previousScreenClass)
        if (!mActivityHelper.resolve(intent)) {
            throw ActivityResolvingException(screen)
        }
        val screenClassFrom = mNavigationFactory.getScreenClass(mActivity)
        val screenClassTo: Class<out Screen?> = screen.javaClass
        val animation =
            getAnimation(TransitionType.REPLACE, screenClassFrom, screenClassTo, animationData)
        mActivityHelper.start(intent, animation)
        mActivityHelper.finish(animation)
        callTransitionListener(TransitionType.REPLACE, screenClassFrom, screenClassTo)
    }

    @Throws(NavigationException::class)
    override fun reset(
        screen: Screen,
        destination: ActivityDestination,
        animationData: AnimationData?
    ) {
        val intent = destination.createIntent(mActivity, screen, null)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        if (!mActivityHelper.resolve(intent)) {
            throw ActivityResolvingException(screen)
        }
        val screenClassFrom = mNavigationFactory.getScreenClass(mActivity)
        val screenClassTo: Class<out Screen?> = screen.javaClass
        val animation =
            getAnimation(TransitionType.RESET, screenClassFrom, screenClassTo, animationData)
        mActivityHelper.start(intent, animation)
        callTransitionListener(TransitionType.RESET, screenClassFrom, screenClassTo)
    }

    @Throws(NavigationException::class)
    override fun goBack(
        screenResult: ScreenResult?,
        animationData: AnimationData?
    ) {
        if (screenResult != null) {
            mScreenResultHelper.setActivityResult(mActivity, screenResult)
        }
        val screenClassFrom = mNavigationFactory.getScreenClass(mActivity)
        val screenClassTo = mNavigationFactory.getPreviousScreenClass(mActivity)
        val animation =
            getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, animationData)
        mActivityHelper.finish(animation)
        callTransitionListener(TransitionType.BACK, screenClassFrom, screenClassTo)
    }

    @Throws(NavigationException::class)
    override fun goBackTo(
        screenClass: Class<out Screen?>,
        destination: ActivityDestination,
        screenResult: ScreenResult?,
        animationData: AnimationData?
    ) {
        val intent = destination.createEmptyIntent(mActivity, screenClass)
            ?: throw ScreenRegistrationException("Can't create intent for a screen " + screenClass.simpleName)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        if (screenResult != null) {
            mScreenResultHelper.setResultToIntent(intent, mActivity, screenResult)
        }
        val screenClassFrom = mNavigationFactory.getScreenClass(mActivity)
        val animation =
            getAnimation(TransitionType.BACK, screenClassFrom, screenClass, animationData)
        mActivityHelper.start(intent, animation)
        callTransitionListener(TransitionType.BACK, screenClassFrom, screenClass)
    }

    private fun getAnimation(
        transitionType: TransitionType,
        screenClassFrom: Class<out Screen?>?,
        screenClassTo: Class<out Screen?>?,
        animationData: AnimationData?
    ): TransitionAnimation {
        return if (screenClassFrom == null || screenClassTo == null) {
            TransitionAnimation.DEFAULT
        } else {
            mAnimationProvider.getAnimation(
                transitionType,
                DestinationType.ACTIVITY,
                screenClassFrom,
                screenClassTo,
                animationData
            )
        }
    }

    private fun callTransitionListener(
        transitionType: TransitionType,
        screenClassFrom: Class<out Screen?>?,
        screenClassTo: Class<out Screen?>?
    ) {
        mTransitionListener.onScreenTransition(
            transitionType,
            DestinationType.ACTIVITY,
            screenClassFrom,
            screenClassTo
        )
    }
}