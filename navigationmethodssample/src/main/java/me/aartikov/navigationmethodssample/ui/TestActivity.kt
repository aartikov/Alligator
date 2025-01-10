package me.aartikov.navigationmethodssample.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.navigationmethodssample.R
import me.aartikov.navigationmethodssample.SampleApplication
import me.aartikov.navigationmethodssample.SampleTransitionAnimationProvider
import me.aartikov.navigationmethodssample.screens.TestScreen
import me.aartikov.navigationmethodssample.screens.TestSmallScreen
import java.util.Random

@RegisterScreen(TestScreen::class)
class TestActivity : AppCompatActivity() {

    private lateinit var mRootView: View
    private lateinit var mCounterTextView: TextView
    private lateinit var mForwardButton: Button
    private lateinit var mReplaceButton: Button
    private lateinit var mResetButton: Button
    private lateinit var mFinishButton: Button

    private val mNavigator: Navigator = SampleApplication.navigator
    private val mNavigationContextBinder: NavigationContextBinder = SampleApplication.navigationContextBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        mRootView = findViewById(R.id.root_view)
        mCounterTextView = findViewById(R.id.counter_text_view)
        mForwardButton = findViewById(R.id.forward_button)
        mReplaceButton = findViewById(R.id.replace_button)
        mResetButton = findViewById(R.id.reset_button)
        mFinishButton = findViewById(R.id.finish_button)

        val screen = SampleApplication.screenResolver.getScreenOrNull<TestScreen>(this)
        val counter = screen?.counter ?: 1
        mCounterTextView.text = getString(R.string.counter_template, counter)

        mForwardButton.setOnClickListener { mNavigator.goForward(TestScreen(counter + 1)) }
        mReplaceButton.setOnClickListener { mNavigator.replace(TestScreen(counter)) }
        mResetButton.setOnClickListener { mNavigator.reset(TestScreen(1)) }
        mFinishButton.setOnClickListener { mNavigator.finish() }

        mRootView.setBackgroundColor(randomColor)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        bindNavigationContext()
        setInitialFragmentIfRequired()
    }

    private fun bindNavigationContext() {
        val navigationContext = NavigationContext.Builder(this, SampleApplication.navigationFactory)
            .fragmentNavigation(supportFragmentManager, R.id.fragment_container)
            .transitionAnimationProvider(SampleTransitionAnimationProvider())
            .build()
        mNavigationContextBinder.bind(navigationContext)
    }

    private fun setInitialFragmentIfRequired() {
        val hasFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) != null
        if (!hasFragment && mNavigator.canExecuteCommandImmediately()) {
            mNavigator.reset(TestSmallScreen(1))
        }
    }

    override fun onPause() {
        mNavigationContextBinder.unbind(this)
        super.onPause()
    }

    override fun onBackPressed() {
        mNavigator.goBack()
    }

    companion object {
        private val randomColor: Int
            get() {
                val random = Random()
                return Color.HSVToColor(floatArrayOf(random.nextInt(360).toFloat(), 0.1f, 1.0f))
            }
    }
}
