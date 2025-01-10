package me.aartikov.flowscreenswitchersample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.Screen
import me.aartikov.alligator.TransitionType
import me.aartikov.flowscreenswitchersample.R
import me.aartikov.flowscreenswitchersample.SampleApplication
import me.aartikov.flowscreenswitchersample.SampleTransitionAnimationProvider
import me.aartikov.flowscreenswitchersample.screens.TestFlowScreen

class MainActivity : AppCompatActivity() {
    private val mNavigator: Navigator = SampleApplication.navigator
    private val mNavigationContextBinder: NavigationContextBinder = SampleApplication.navigationContextBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        bindNavigationContext()
        setInitialFragmentIfRequired()
    }

    private fun bindNavigationContext() {
        val builder = NavigationContext.Builder(this, SampleApplication.navigationFactory)

        val currentFlowFragment = currentFlowFragment

        if (currentFlowFragment is ContainerIdProvider) {
            var containerId = (currentFlowFragment as ContainerIdProvider).containerId
            var childFragmentManager = currentFlowFragment.childFragmentManager

            val childFragment = childFragmentManager.findFragmentById(containerId)

            if (childFragment is ContainerIdProvider) {
                childFragmentManager = childFragment.childFragmentManager
                containerId = (childFragment as ContainerIdProvider).containerId
            }

            builder.fragmentNavigation(childFragmentManager, containerId)
        }

        if (currentFlowFragment is ScreenSwitcherProvider) {
            builder.screenSwitcher((currentFlowFragment as ScreenSwitcherProvider).screenSwitcher)
        }

        builder.flowFragmentNavigation(supportFragmentManager, R.id.flow_fragment_container)
            .transitionListener { _: TransitionType?, destinationType: DestinationType, _: Class<out Screen?>?, _: Class<out Screen?>? ->
                if (destinationType == DestinationType.FLOW_FRAGMENT) {
                    bindNavigationContext() // rebind NavigationContext because a current flow fragment has been changed.
                } else {
                    if (currentFlowFragment is ContainerIdProvider) {
                        val containerId = (currentFlowFragment as ContainerIdProvider).containerId
                        val childFragmentManager = currentFlowFragment.childFragmentManager

                        val childFragment = childFragmentManager.findFragmentById(containerId)

                        if (destinationType == DestinationType.FRAGMENT && childFragment is ContainerIdProvider) {
                            bindNavigationContext()
                        }
                    }
                }
            }
            .screenSwitchingListener { _: Screen?, _: Screen? -> bindNavigationContext() }
            .transitionAnimationProvider(SampleTransitionAnimationProvider())

        mNavigationContextBinder.bind(builder.build())
    }

    private fun setInitialFragmentIfRequired() {
        if (currentFlowFragment == null && mNavigator.canExecuteCommandImmediately()) {
            mNavigator.reset(TestFlowScreen())
        }
    }

    private val currentFlowFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.flow_fragment_container)

    override fun onPause() {
        mNavigationContextBinder.unbind(this)
        super.onPause()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        mNavigator.goBack()
    }
}
