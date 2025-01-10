package me.aartikov.simplenavigationsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.simplenavigationsample.R
import me.aartikov.simplenavigationsample.SampleApplication
import me.aartikov.simplenavigationsample.SampleTransitionAnimationProvider
import me.aartikov.simplenavigationsample.screens.ScreenB
import me.aartikov.simplenavigationsample.screens.ScreenC

@RegisterScreen(ScreenB::class)
class ActivityB : AppCompatActivity() {
    private val mNavigator = SampleApplication.navigator
    private val mNavigationContextBinder = SampleApplication.navigationContextBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        if (savedInstanceState == null) {
            mNavigator.reset(ScreenC())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val navigationContext = NavigationContext.Builder(this, SampleApplication.navigationFactory)
            .fragmentNavigation(supportFragmentManager, R.id.fragment_container)
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
