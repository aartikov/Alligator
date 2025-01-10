package me.aartikov.flowsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import me.aartikov.alligator.DestinationType
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.Screen
import me.aartikov.alligator.TransitionType
import me.aartikov.alligator.listeners.TransitionListener
import me.aartikov.flowsample.R
import me.aartikov.flowsample.SampleApplication
import me.aartikov.flowsample.SampleTransitionAnimationProvider
import me.aartikov.flowsample.screens.TestFlowScreen

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
            builder.fragmentNavigation(
                currentFlowFragment.childFragmentManager,
                (currentFlowFragment as ContainerIdProvider).containerId
            )
        }

        builder.flowFragmentNavigation(supportFragmentManager, R.id.flow_fragment_container)
            .transitionAnimationProvider(SampleTransitionAnimationProvider())
            .transitionListener { _: TransitionType?, destinationType: DestinationType, _: Class<out Screen?>?, _: Class<out Screen?>? ->
                if (destinationType == DestinationType.FLOW_FRAGMENT) {
                    bindNavigationContext() // rebind NavigationContext because a current flow fragment has been changed.
                }
            }

        mNavigationContextBinder.bind(builder.build())
    }

    private fun setInitialFragmentIfRequired() {
        if (currentFlowFragment == null && mNavigator.canExecuteCommandImmediately()) {
            mNavigator.reset(TestFlowScreen(1))
        }
    }

    private val currentFlowFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.flow_fragment_container)

    override fun onPause() {
        mNavigationContextBinder.unbind(this)
        super.onPause()
    }

    override fun onBackPressed() {
        mNavigator.goBack()
    }
}
