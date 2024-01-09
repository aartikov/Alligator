package me.aartikov.alligator

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import me.aartikov.alligator.animations.providers.DefaultDialogAnimationProvider
import me.aartikov.alligator.animations.providers.DefaultTransitionAnimationProvider
import me.aartikov.alligator.animations.providers.DialogAnimationProvider
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider
import me.aartikov.alligator.listeners.DefaultDialogShowingListener
import me.aartikov.alligator.listeners.DefaultNavigationErrorListener
import me.aartikov.alligator.listeners.DefaultScreenResultListener
import me.aartikov.alligator.listeners.DefaultScreenSwitchingListener
import me.aartikov.alligator.listeners.DefaultTransitionListener
import me.aartikov.alligator.listeners.DialogShowingListener
import me.aartikov.alligator.listeners.NavigationErrorListener
import me.aartikov.alligator.listeners.ScreenResultListener
import me.aartikov.alligator.listeners.ScreenSwitchingListener
import me.aartikov.alligator.listeners.TransitionListener
import me.aartikov.alligator.navigationfactories.NavigationFactory
import me.aartikov.alligator.navigators.ActivityNavigator
import me.aartikov.alligator.navigators.DefaultActivityNavigator
import me.aartikov.alligator.navigators.DefaultDialogFragmentNavigator
import me.aartikov.alligator.navigators.DefaultFragmentNavigator
import me.aartikov.alligator.navigators.DialogFragmentNavigator
import me.aartikov.alligator.navigators.FragmentNavigator
import me.aartikov.alligator.screenswitchers.ScreenSwitcher

/**
 * Used to configure an [AndroidNavigator].
 */
class NavigationContext private constructor(
    val activity: AppCompatActivity,
    val navigationFactory: NavigationFactory,
    val activityNavigator: ActivityNavigator,
    val fragmentNavigator: FragmentNavigator?,
    val flowFragmentNavigator: FragmentNavigator?,
    val dialogFragmentNavigator: DialogFragmentNavigator,
    val screenSwitcher: ScreenSwitcher?,
    val screenSwitchingListener: ScreenSwitchingListener,
    val screenResultListener: ScreenResultListener,
    val navigationErrorListener: NavigationErrorListener
) {

    /**
     * Builder for a [NavigationContext].
     */
    class Builder
    /**
     * Creates with the given activity.
     *
     * @param activity          activity that should be current when the navigation context is bound.
     * @param navigationFactory navigation factory that was used to create [AndroidNavigator]
     */(
        private val mActivity: AppCompatActivity,
        private val mNavigationFactory: NavigationFactory
    ) {
        private var mFragmentManager: FragmentManager? = null
        private var mFragmentContainerId = 0
        private var mFlowFragmentManager: FragmentManager? = null
        private var mFlowFragmentContainerId = 0
        private var mScreenSwitcher: ScreenSwitcher? = null
        private var mTransitionAnimationProvider: TransitionAnimationProvider? = null
        private var mDialogAnimationProvider: DialogAnimationProvider? = null
        private var mTransitionListener: TransitionListener? = null
        private var mScreenResultListener: ScreenResultListener? = null
        private var mDialogShowingListener: DialogShowingListener? = null
        private var mScreenSwitchingListener: ScreenSwitchingListener? = null
        private var mNavigationErrorListener: NavigationErrorListener? = null

        /**
         * Configure fragment navigation
         *
         * @param fragmentManager that will be used for fragment transactions
         * @param containerId     container id for fragments
         * @return this object
         */
        fun fragmentNavigation(fragmentManager: FragmentManager, @IdRes containerId: Int): Builder {
            mFragmentManager = fragmentManager
            mFragmentContainerId = containerId
            return this
        }

        /**
         * Configure flow fragment navigation
         *
         * @param fragmentManager that will be used for flow fragment transactions
         * @param containerId     container id for fragments
         * @return this object
         */
        fun flowFragmentNavigation(
            fragmentManager: FragmentManager,
            @IdRes containerId: Int
        ): Builder {
            mFlowFragmentManager = fragmentManager
            mFlowFragmentContainerId = containerId
            return this
        }

        /**
         * Sets a screen switcher.
         *
         * @param screenSwitcher screen switcher that will be used to switch screens by `switchTo` method of [Navigator]
         * @return this object
         */
        fun screenSwitcher(screenSwitcher: ScreenSwitcher?): Builder {
            mScreenSwitcher = screenSwitcher
            return this
        }

        /**
         * Sets a provider of [TransitionAnimation]s.
         *
         * @param transitionAnimationProvider provider of [TransitionAnimation]s. By default a provider that returns `TransitionAnimation.DEFAULT` is used.
         * @return this object
         */
        fun transitionAnimationProvider(transitionAnimationProvider: TransitionAnimationProvider?): Builder {
            mTransitionAnimationProvider = transitionAnimationProvider
            return this
        }

        /**
         * Sets a provider of [DialogAnimation]s.
         *
         * @param dialogAnimationProvider provider of [DialogAnimation]s. By default a provider that returns `DialogAnimation.DEFAULT` is used.
         * @return this object
         */
        fun dialogAnimationProvider(dialogAnimationProvider: DialogAnimationProvider?): Builder {
            mDialogAnimationProvider = dialogAnimationProvider
            return this
        }

        /**
         * Sets a transition listener. This listener is called after a screen transition.
         *
         * @param transitionListener transition listener.
         * @return this object
         */
        fun transitionListener(transitionListener: TransitionListener?): Builder {
            mTransitionListener = transitionListener
            return this
        }

        /**
         * Sets a dialog showing listener. This listener is called after a dialog has been shown.
         *
         * @param dialogShowingListener dialog showing listener.
         * @return this object
         */
        fun dialogShowingListener(dialogShowingListener: DialogShowingListener?): Builder {
            mDialogShowingListener = dialogShowingListener
            return this
        }

        /**
         * Sets a screen switching listener listener. This listener is called after a screen has been switched using [ScreenSwitcher].
         *
         * @param screenSwitchingListener screen switcher listener.
         * @return this object
         */
        fun screenSwitchingListener(screenSwitchingListener: ScreenSwitchingListener?): Builder {
            mScreenSwitchingListener = screenSwitchingListener
            return this
        }

        /**
         * Sets a screen result listener. This listener is called when a screen returned result to a previous screen
         *
         * @param screenResultListener screenResultListener screen result listener.
         * @return this object
         */
        fun screenResultListener(screenResultListener: ScreenResultListener?): Builder {
            mScreenResultListener = screenResultListener
            return this
        }

        /**
         * Sets a navigation error listener. This listener is called when an error has occurred during [Command] execution.
         *
         * @param navigationErrorListener navigation error listener. By default a listener that wraps errors to `RuntimeException` and throws it is used.
         * @return this object
         */
        fun navigationErrorListener(navigationErrorListener: NavigationErrorListener?): Builder {
            mNavigationErrorListener = navigationErrorListener
            return this
        }

        /**
         * Builds a navigation context
         *
         * @return created navigation context
         */
        fun build(): NavigationContext {
            val transitionListener =
                if (mTransitionListener != null) mTransitionListener!! else DefaultTransitionListener()
            val transitionAnimationProvider =
                if (mTransitionAnimationProvider != null) mTransitionAnimationProvider!! else DefaultTransitionAnimationProvider()
            val dialogShowingListener =
                if (mDialogShowingListener != null) mDialogShowingListener!! else DefaultDialogShowingListener()
            val dialogAnimationProvider =
                if (mDialogAnimationProvider != null) mDialogAnimationProvider!! else DefaultDialogAnimationProvider()
            val screenSwitchingListener =
                if (mScreenSwitchingListener != null) mScreenSwitchingListener!! else DefaultScreenSwitchingListener()
            val screenResultListener =
                if (mScreenResultListener != null) mScreenResultListener!! else DefaultScreenResultListener()
            val navigationErrorListener =
                if (mNavigationErrorListener != null) mNavigationErrorListener!! else DefaultNavigationErrorListener()
            val activityNavigator: ActivityNavigator = DefaultActivityNavigator(
                mActivity,
                mNavigationFactory,
                transitionListener,
                transitionAnimationProvider
            )
            val fragmentNavigator: FragmentNavigator? =
                if (mFragmentManager != null) DefaultFragmentNavigator(
                    false,
                    mFragmentManager!!,
                    mFragmentContainerId,
                    mNavigationFactory,
                    transitionListener,
                    screenResultListener,
                    transitionAnimationProvider
                ) else null
            val flowFragmentNavigator: FragmentNavigator? =
                if (mFlowFragmentManager != null) DefaultFragmentNavigator(
                    true,
                    mFlowFragmentManager!!,
                    mFlowFragmentContainerId,
                    mNavigationFactory,
                    transitionListener,
                    screenResultListener,
                    transitionAnimationProvider
                ) else null
            val dialogFragmentNavigator: DialogFragmentNavigator = DefaultDialogFragmentNavigator(
                mActivity.supportFragmentManager, mNavigationFactory,
                dialogShowingListener, screenResultListener, dialogAnimationProvider
            )
            return NavigationContext(
                mActivity,
                mNavigationFactory,
                activityNavigator,
                fragmentNavigator,
                flowFragmentNavigator,
                dialogFragmentNavigator,
                mScreenSwitcher,
                screenSwitchingListener,
                screenResultListener,
                navigationErrorListener
            )
        }
    }
}