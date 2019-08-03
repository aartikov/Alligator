package exp.terenfear.singleactivitynavigation

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import exp.terenfear.singleactivitynavigation.flowcontainer.FlowContainer
import exp.terenfear.singleactivitynavigation.flowcontainer.letter.LetterFlowScreen
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.flowmanagers.FragmentFlowManager
import me.aartikov.alligator.listeners.ScreenResultListener
import me.aartikov.alligator.listeners.TransitionListener


class MainActivity : AppCompatActivity() {

    private lateinit var flowManager: FragmentFlowManager
    private lateinit var navigationContext: NavigationContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flowManager = FragmentFlowManager(App.navigatorFactory, supportFragmentManager, R.id.container)
        if (savedInstanceState == null) {
            App.navigator.resetFlow(LetterFlowScreen())
        }
    }

    override fun onBackPressed() {
        App.navigator.goBack()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        bindNavigationContext()
    }

    override fun onPause() {
        App.navigationContextBinder.unbind(this)
        super.onPause()
    }

    private fun bindNavigationContext() {
        val builder = NavigationContext.Builder(this)
                .flowManager(flowManager)
                .flowTransitionListener(flowTransitionListener)
                .screenResultListener(resultListener)
                .transitionAnimationProvider(SampleTransitionAnimationProvider())
                .transitionListener { transitionType, screenClassFrom, screenClassTo, isActivity ->
                    logFragmentStack()
                }

        val currentFlowFragment = flowManager.currentFragment
        if (currentFlowFragment is FlowContainer) {
            builder.containerId((currentFlowFragment as FlowContainer).flowContainerId)
                    .fragmentManager(currentFlowFragment.childFragmentManager)       // Use child fragment manager for nested navigation
        }
        navigationContext = builder.build()
        App.navigationContextBinder.bind(navigationContext)
    }

    private fun logFragmentStack() {
        val fragmentStack = navigationContext.fragmentStack?.fragments?.joinToString(separator = "->") { it.javaClass.simpleName }
        Log.d(this.javaClass.simpleName, "fragment stack: $fragmentStack")
        findViewById<TextView>(R.id.vFragmentStack)?.text = fragmentStack
    }

    private val flowTransitionListener = TransitionListener { transitionType, screenClassFrom, screenClassTo, isActivity ->
        bindNavigationContext()
        logFragmentStack()
    }

    private val resultListener = ScreenResultListener { screenClass, result ->
        val currentFlowFragment = flowManager.currentFragment
        (currentFlowFragment as? FlowContainer)?.flowResultListener?.onScreenResult(screenClass, result)
    }

}
