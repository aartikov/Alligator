package me.aartikov.advancedscreenswitchersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import me.aartikov.advancedscreenswitchersample.R
import me.aartikov.advancedscreenswitchersample.SampleApplication
import me.aartikov.advancedscreenswitchersample.screens.InnerScreen
import me.aartikov.advancedscreenswitchersample.screens.TabScreen
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.annotations.RegisterScreen

@RegisterScreen(TabScreen::class)
class TabFragment : Fragment(), ContainerIdProvider {

    private lateinit var mNameTextView: TextView

    private val mNavigator: Navigator = SampleApplication.navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNameTextView = view.findViewById(R.id.name_text_view)

        val screen = SampleApplication.screenResolver.getScreen<TabScreen>(this)
        mNameTextView.text = screen.name
    }

    override fun onResume() {
        super.onResume()
        setInitialFragmentIfRequired()
    }

    private fun setInitialFragmentIfRequired() {
        val hasFragment = childFragmentManager.findFragmentById(R.id.inner_container) != null
        if (!hasFragment && mNavigator.canExecuteCommandImmediately()) {
            mNavigator.reset(InnerScreen(1))
        }
    }

    override val containerId: Int
        get() = R.id.inner_container
}
