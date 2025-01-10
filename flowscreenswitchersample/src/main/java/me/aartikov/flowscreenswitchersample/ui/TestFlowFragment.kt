package me.aartikov.flowscreenswitchersample.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.Screen
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher
import me.aartikov.alligator.screenswitchers.ScreenSwitcher
import me.aartikov.flowscreenswitchersample.R
import me.aartikov.flowscreenswitchersample.SampleApplication
import me.aartikov.flowscreenswitchersample.screens.TabScreen
import me.aartikov.flowscreenswitchersample.screens.TestFlowScreen
import java.util.Random

@RegisterScreen(TestFlowScreen::class)
class TestFlowFragment : Fragment(),
    ContainerIdProvider,
    ScreenSwitcherProvider {

    private lateinit var mRootView: View
    private lateinit var bottomBarFirst: BottomNavigationView

    private val mNavigator: Navigator = SampleApplication.navigator
    private lateinit var mScreenSwitcher: FragmentScreenSwitcher

    private val mTabScreenMap: MutableMap<Int, Screen> = LinkedHashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTabScreenMap()
        mScreenSwitcher = FragmentScreenSwitcher(
            SampleApplication.navigationFactory,
            childFragmentManager,
            R.id.fragment_container
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view)
        bottomBarFirst = view.findViewById(R.id.bottomBarFirst)

        bottomBarFirst.setOnItemSelectedListener { menuItem: MenuItem ->
            val screen = getTabScreen(menuItem.itemId)
            mNavigator.switchTo(screen)
            true
        }

        mRootView.setBackgroundColor(randomColor)
    }

    override val containerId: Int
        get() = R.id.fragment_container

    override val screenSwitcher: ScreenSwitcher
        get() = mScreenSwitcher

    private fun getTabScreen(tabId: Int): Screen {
        return mTabScreenMap.getValue(tabId)
    }

    companion object {
        private val randomColor: Int
            get() {
                val random = Random()
                return Color.HSVToColor(floatArrayOf(random.nextInt(360).toFloat(), 0.1f, 1.0f))
            }
    }
}
