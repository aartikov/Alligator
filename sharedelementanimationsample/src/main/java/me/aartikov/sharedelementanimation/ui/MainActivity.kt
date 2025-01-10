package me.aartikov.sharedelementanimation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.sharedelementanimation.R
import me.aartikov.sharedelementanimation.SampleApplication
import me.aartikov.sharedelementanimation.SampleTransitionAnimationProvider
import me.aartikov.sharedelementanimation.screens.FirstScreen

class MainActivity : AppCompatActivity() {
    private val mNavigator: Navigator = SampleApplication.navigator
    private val mNavigationContextBinder: NavigationContextBinder = SampleApplication.navigationContextBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            mNavigator.reset(FirstScreen())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val navigationContext = NavigationContext.Builder(this, SampleApplication.navigationFactory)
            .fragmentNavigation(supportFragmentManager, R.id.fragment_container)
            .transitionAnimationProvider(SampleTransitionAnimationProvider(this))
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
