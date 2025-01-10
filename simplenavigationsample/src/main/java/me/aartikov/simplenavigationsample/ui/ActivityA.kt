package me.aartikov.simplenavigationsample.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.simplenavigationsample.R
import me.aartikov.simplenavigationsample.SampleApplication
import me.aartikov.simplenavigationsample.SampleTransitionAnimationProvider
import me.aartikov.simplenavigationsample.screens.ScreenA
import me.aartikov.simplenavigationsample.screens.ScreenB

@RegisterScreen(ScreenA::class)
class ActivityA : AppCompatActivity() {
    private val mNavigator = SampleApplication.navigator
    private val mNavigationContextBinder = SampleApplication.navigationContextBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        setTitle(R.string.screen_a)

        val goForwardToBButton = findViewById<Button>(R.id.go_forward_to_b_button)
        goForwardToBButton.setOnClickListener { mNavigator.goForward(ScreenB()) } // If you use MVP architectural pattern call methods of Navigator in presenters.
    }

    // Bind NavigationContext in onResumeFragments() and unbind it in onPause().
    // In a real application you can do it in a base activity class, or use plugin system like that https://github.com/passsy/CompositeAndroid
    override fun onResumeFragments() {
        super.onResumeFragments()
        val navigationContext = NavigationContext.Builder(this, SampleApplication.navigationFactory)
            .transitionAnimationProvider(SampleTransitionAnimationProvider())
            .build()
        mNavigationContextBinder.bind(navigationContext)
    }

    override fun onPause() {
        mNavigationContextBinder.unbind(this)
        super.onPause()
    }

    override fun onBackPressed() {
        mNavigator.goBack()
    }
}
