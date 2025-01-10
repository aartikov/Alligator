package me.aartikov.simplescreenswitchersample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.Screen
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.alligator.listeners.ScreenSwitchingListener
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher
import me.aartikov.simplescreenswitchersample.R
import me.aartikov.simplescreenswitchersample.SampleApplication
import me.aartikov.simplescreenswitchersample.screens.MainScreen
import me.aartikov.simplescreenswitchersample.screens.TabScreen.Android
import me.aartikov.simplescreenswitchersample.screens.TabScreen.Bug
import me.aartikov.simplescreenswitchersample.screens.TabScreen.Dog

@RegisterScreen(MainScreen::class)
class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    ScreenSwitchingListener {

    private lateinit var mBottomBar: BottomNavigationView

    private val mNavigator = SampleApplication.navigator
    private val mNavigationContextBinder = SampleApplication.navigationContextBinder
    private val mNavigationFactory = SampleApplication.navigationFactory
    private lateinit var mScreenSwitcher: FragmentScreenSwitcher

    @SuppressLint("UseSparseArrays")
    private val mTabScreenMap: MutableMap<Int, Screen> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomBar = findViewById(R.id.bottom_bar)

        initTabScreenMap()
        mBottomBar.setOnItemSelectedListener(this)
        mScreenSwitcher = FragmentScreenSwitcher(
            mNavigationFactory,
            supportFragmentManager, R.id.main_container
        )

        if (savedInstanceState == null) {
            mNavigator.switchTo(getTabScreen(R.id.tab_android))
        }
    }

    private fun initTabScreenMap() {
        mTabScreenMap[R.id.tab_android] = Android()
        mTabScreenMap[R.id.tab_bug] = Bug()
        mTabScreenMap[R.id.tab_dog] = Dog()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val navigationContext = NavigationContext.Builder(this, mNavigationFactory)
            .screenSwitcher(mScreenSwitcher)
            .screenSwitchingListener(this)
            .build()
        mNavigationContextBinder.bind(navigationContext)
    }

    override fun onPause() {
        mNavigationContextBinder.unbind(this)
        super.onPause()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val screen = getTabScreen(item.itemId)
        mNavigator.switchTo(screen)
        return true
    }

    override fun onScreenSwitched(screenFrom: Screen?, screenTo: Screen) {
        val tabId = getTabId(screenTo)
        mBottomBar.menu.findItem(tabId).setChecked(true)
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        mNavigator.goBack()
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
}
