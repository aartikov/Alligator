package me.aartikov.flowsample.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.flowsample.R
import me.aartikov.flowsample.SampleApplication
import me.aartikov.flowsample.screens.TestFlowScreen
import me.aartikov.flowsample.screens.TestSmallScreen
import java.util.Random

@RegisterScreen(TestFlowScreen::class)
class TestFlowFragment : Fragment(), ContainerIdProvider {

    private lateinit var mRootView: View
    private lateinit var mCounterTextView: TextView
    private lateinit var mForwardButton: Button
    private lateinit var mReplaceButton: Button
    private lateinit var mResetButton: Button
    private lateinit var mFinishButton: Button
    private lateinit var mFinishAllButton: Button

    private val mNavigator: Navigator = SampleApplication.navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRootView = view.findViewById(R.id.root_view)
        mCounterTextView = view.findViewById(R.id.counter_text_view)
        mForwardButton = view.findViewById(R.id.forward_button)
        mReplaceButton = view.findViewById(R.id.replace_button)
        mResetButton = view.findViewById(R.id.reset_button)
        mFinishButton = view.findViewById(R.id.finish_button)
        mFinishAllButton = view.findViewById(R.id.finish_all_button)

        val screen = SampleApplication.screenResolver.getScreen<TestFlowScreen>(this)
        val counter = screen.counter
        mCounterTextView.text = getString(R.string.counter_template, counter)

        mForwardButton.setOnClickListener { mNavigator.goForward(TestFlowScreen(counter + 1)) }
        mReplaceButton.setOnClickListener { mNavigator.replace(TestFlowScreen(counter)) }
        mResetButton.setOnClickListener { mNavigator.reset(TestFlowScreen(1)) }
        mFinishButton.setOnClickListener { mNavigator.finish() }
        mFinishAllButton.setOnClickListener { mNavigator.finishTopLevel() }

        mRootView.setBackgroundColor(randomColor)
    }

    override fun onResume() {
        super.onResume()
        setInitialFragmentIfRequired()
    }

    private fun setInitialFragmentIfRequired() {
        if (currentFragment == null && mNavigator.canExecuteCommandImmediately()) {
            mNavigator.reset(TestSmallScreen(1))
        }
    }

    private val currentFragment: Fragment?
        get() = childFragmentManager.findFragmentById(R.id.fragment_container)

    override val containerId: Int
        get() = R.id.fragment_container

    companion object {
        private val randomColor: Int
            get() {
                val random = Random()
                return Color.HSVToColor(floatArrayOf(random.nextInt(360).toFloat(), 0.1f, 1.0f))
            }
    }
}
