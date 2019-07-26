package exp.terenfear.singleactivitynavigation

import android.app.Application
import me.aartikov.alligator.AndroidNavigator
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.ScreenResolver
import me.aartikov.alligator.navigationfactories.GeneratedNavigationFactory
import me.aartikov.alligator.navigationfactories.NavigationFactory

/**
 * Date: 22.07.2019
 * Time: 19:34
 *
 * @author Terenfear
 */
class App : Application() {
    companion object {
        private lateinit var androidNavigator: AndroidNavigator
        val navigator: Navigator by lazy { androidNavigator }
        val navigatorFactory: NavigationFactory by lazy { androidNavigator.navigationFactory }
        val navigationContextBinder: NavigationContextBinder by lazy { androidNavigator }
        val screenResolver: ScreenResolver by lazy { androidNavigator.screenResolver }
    }

    override fun onCreate() {
        super.onCreate()
        androidNavigator = AndroidNavigator(GeneratedNavigationFactory())
    }
}