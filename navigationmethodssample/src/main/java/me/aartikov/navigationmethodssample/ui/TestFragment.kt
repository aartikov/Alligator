package me.aartikov.navigationmethodssample.ui

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
import me.aartikov.navigationmethodssample.R
import me.aartikov.navigationmethodssample.SampleApplication
import me.aartikov.navigationmethodssample.screens.TestSmallScreen
import java.util.Random

@RegisterScreen(TestSmallScreen::class)
class TestFragment : Fragment() {

    private lateinit var mRootView: View
    private lateinit var mCounterTextView: TextView
    private lateinit var mForwardButton: Button
    private lateinit var mReplaceButton: Button
    private lateinit var mResetButton: Button
    private lateinit var mBackButton: Button
    private lateinit var mDoubleBackButton: Button

    private val mNavigator: Navigator = SampleApplication.navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRootView = view.findViewById(R.id.root_view)
        mCounterTextView = view.findViewById(R.id.counter_text_view)
        mForwardButton = view.findViewById(R.id.forward_button)
        mReplaceButton = view.findViewById(R.id.replace_button)
        mResetButton = view.findViewById(R.id.reset_button)
        mBackButton = view.findViewById(R.id.back_button)
        mDoubleBackButton = view.findViewById(R.id.double_back_button)

        val screen = SampleApplication.screenResolver.getScreen<TestSmallScreen>(this)
        val counter = screen.counter
        mCounterTextView.text = getString(R.string.counter_template, counter)

        mForwardButton.setOnClickListener { mNavigator.goForward(TestSmallScreen(counter + 1)) }
        mReplaceButton.setOnClickListener { mNavigator.replace(TestSmallScreen(counter)) }
        mResetButton.setOnClickListener { mNavigator.reset(TestSmallScreen(1)) }
        mBackButton.setOnClickListener { mNavigator.goBack() }
        mDoubleBackButton.setOnClickListener {
            mNavigator.goBack() // Due to a command queue in AndroidNavigator you can combine navigation methods arbitrarily.
            mNavigator.goBack()
        }

        mRootView.setBackgroundColor(randomColor)
    }

    companion object {
        private val randomColor: Int
            get() {
                val random = Random()
                return Color.HSVToColor(floatArrayOf(random.nextInt(360).toFloat(), 0.1f, 0.8f))
            }
    }
}
