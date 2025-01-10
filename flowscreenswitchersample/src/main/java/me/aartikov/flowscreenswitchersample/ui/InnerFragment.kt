package me.aartikov.flowscreenswitchersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.flowscreenswitchersample.R
import me.aartikov.flowscreenswitchersample.SampleApplication
import me.aartikov.flowscreenswitchersample.screens.InnerScreen
import me.aartikov.flowscreenswitchersample.screens.TestFlow2Screen

@RegisterScreen(InnerScreen::class)
class InnerFragment : Fragment() {

    private lateinit var mCounterTextView: TextView
    private lateinit var mForwardButton: Button
    private lateinit var mForwardFlowButton: Button

    private val mNavigator: Navigator = SampleApplication.navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCounterTextView = view.findViewById(R.id.counter_text_view)
        mForwardButton = view.findViewById(R.id.forward_button)
        mForwardFlowButton = view.findViewById(R.id.forward_flow_button)

        val screen = SampleApplication.screenResolver.getScreen<InnerScreen>(this)
        val counter = screen.counter
        mCounterTextView.text = getString(R.string.counter_template, counter)
        mForwardButton.setOnClickListener { mNavigator.goForward(InnerScreen(counter + 1)) }

        mForwardFlowButton.setOnClickListener { mNavigator.goForward(TestFlow2Screen()) }
    }
}
