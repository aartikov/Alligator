package me.aartikov.advancedscreenswitchersample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.aartikov.advancedscreenswitchersample.R
import me.aartikov.advancedscreenswitchersample.SampleApplication
import me.aartikov.advancedscreenswitchersample.SampleScreenSwitcherAnimationProvider
import me.aartikov.advancedscreenswitchersample.SampleTransitionAnimationProvider
import me.aartikov.advancedscreenswitchersample.screens.MainScreen
import me.aartikov.advancedscreenswitchersample.screens.TabScreen
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.Screen
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.alligator.listeners.ScreenSwitchingListener
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher

@RegisterScreen(MainScreen::class)
class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    ScreenSwitchingListener {

    private lateinit var mBottomBar: BottomNavigationView

    private val mNavigator: Navigator = SampleApplication.navigator
    private val mNavigationContextBinder: NavigationContextBinder = SampleApplication.navigationContextBinder
    private lateinit var mScreenSwitcher: FragmentScreenSwitcher

    @SuppressLint("UseSparseArrays")
    private val mTabScreenMap: MutableMap<Int, Screen> = LinkedHashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomBar = findViewById(R.id.bottom_bar)

        initTabScreenMap()
        mBottomBar.setOnItemSelectedListener(this)
        mScreenSwitcher = FragmentScreenSwitcher(
            SampleApplication.navigationFactory,
            supportFragmentManager,
            R.id.main_container, SampleScreenSwitcherAnimationProvider(tabScreens)
        )

        if (savedInstanceState == null) {
            mNavigator.switchTo(getTabScreen(R.id.tab_android))
        }
    }

    private fun initTabScreenMap() {
        mTabScreenMap[R.id.tab_android] = TabScreen(getString(R.string.tab_android))
        mTabScreenMap[R.id.tab_bug] = TabScreen(getString(R.string.tab_bug))
        mTabScreenMap[R.id.tab_dog] = TabScreen(getString(R.string.tab_dog))
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        bindNavigationContext()
    }

    override fun onPause() {
        mNavigationContextBinder.unbind(this)
        super.onPause()
    }

    private fun bindNavigationContext() {
        val builder = NavigationContext.Builder(this, SampleApplication.navigationFactory)
            .screenSwitcher(mScreenSwitcher)
            .screenSwitchingListener(this)
            .transitionAnimationProvider(SampleTransitionAnimationProvider())

        val fragment = mScreenSwitcher.currentFragment
        if (fragment is ContainerIdProvider) {
            builder.fragmentNavigation(
                fragment.childFragmentManager,
                (fragment as ContainerIdProvider).containerId
            ) // Use child fragment manager for nested navigation
        }

        mNavigationContextBinder.bind(builder.build())
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        mNavigator.goBack()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val screen = getTabScreen(item.itemId)
        mNavigator.switchTo(screen)
        return false
    }

    override fun onScreenSwitched(screenFrom: Screen?, screenTo: Screen) {
        val tabId = getTabId(screenTo)
        mBottomBar.menu.findItem(tabId).setChecked(true)
        bindNavigationContext() // rebind NavigationContext because we need to set another container id and another child fragment manager.
    }

    private fun getTabScreen(tabId: Int): Screen {
        return mTabScreenMap.getValue(tabId)
    }

    private fun getTabId(tabScreen: Screen): Int {
        for ((key, value) in mTabScreenMap) {
            if (tabScreen == value) {
                return key
            }
        }
        return -1
    }

    private val tabScreens: List<Screen>
        get() = ArrayList(mTabScreenMap.values)
}
