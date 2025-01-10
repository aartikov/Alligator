package me.aartikov.flowscreenswitchersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.flowscreenswitchersample.R
import me.aartikov.flowscreenswitchersample.SampleApplication
import me.aartikov.flowscreenswitchersample.screens.InnerScreen
import me.aartikov.flowscreenswitchersample.screens.TabScreen

@RegisterScreen(TabScreen::class)
class TabFragment : Fragment(), ContainerIdProvider {

    private lateinit var mNameTextView: TextView

    private val mNavigator: Navigator = SampleApplication.navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            mNavigator.reset(InnerScreen(counter = 1))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNameTextView = view.findViewById(R.id.name_text_view)

        val screen = SampleApplication.screenResolver.getScreen<TabScreen>(this)
        mNameTextView.text = screen.name
    }

    override val containerId: Int
        get() = R.id.inner_container
}
