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
import me.aartikov.flowscreenswitchersample.screens.TestFlow2Screen
import java.util.Random

@RegisterScreen(TestFlow2Screen::class)
class TestFlow2Fragment : Fragment(),
    ContainerIdProvider,
    ScreenSwitcherProvider {

    private lateinit var mRootView: View

    private lateinit var bottomBarSecond: BottomNavigationView

    private val mNavigator: Navigator = SampleApplication.navigator
    private lateinit var mScreenSwitcher: FragmentScreenSwitcher

    private val mTabScreenMap: MutableMap<Int, Screen> = LinkedHashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTabScreenMap()
        mScreenSwitcher = FragmentScreenSwitcher(
            SampleApplication.navigationFactory,
            childFragmentManager,
            R.id.fragment_container2
        )

        if (savedInstanceState == null) {
            mNavigator.switchTo(getTabScreen(R.id.tab1_3d))
        }
    }

    private fun initTabScreenMap() {
        mTabScreenMap[R.id.tab1_3d] = TabScreen(getString(R.string.tab_3d))
        mTabScreenMap[R.id.tab2_4k] = TabScreen(getString(R.string.tab_4k))
        mTabScreenMap[R.id.tab3_360] = TabScreen(getString(R.string.tab_360))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_flow2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRootView = view.findViewById(R.id.root_view2)
        bottomBarSecond = view.findViewById(R.id.bottomBarSecond)

        bottomBarSecond.setOnItemSelectedListener { menuItem: MenuItem ->
            val screen = getTabScreen(menuItem.itemId)
            mNavigator.switchTo(screen)
            true
        }

        mRootView.setBackgroundColor(randomColor)
    }

    override val containerId: Int
        get() = R.id.fragment_container2

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
