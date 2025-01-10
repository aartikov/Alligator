package me.aartikov.screenresultsample

import android.app.Application
import me.aartikov.alligator.ActivityResultHandler
import me.aartikov.alligator.AndroidNavigator
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.navigationfactories.NavigationFactory

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        sAndroidNavigator = AndroidNavigator(SampleNavigationFactory())
    }

    companion object {
        private lateinit var sAndroidNavigator: AndroidNavigator

        val navigator: Navigator
            get() = sAndroidNavigator

        val navigationFactory: NavigationFactory
            get() = sAndroidNavigator.navigationFactory

        val navigationContextBinder: NavigationContextBinder
            get() = sAndroidNavigator

        val activityResultHandler: ActivityResultHandler
            get() = sAndroidNavigator.activityResultHandler
    }
}
