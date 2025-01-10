package me.aartikov.advancedscreenswitchersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import me.aartikov.advancedscreenswitchersample.R
import me.aartikov.advancedscreenswitchersample.SampleApplication
import me.aartikov.advancedscreenswitchersample.screens.InnerScreen
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.annotations.RegisterScreen

@RegisterScreen(InnerScreen::class)
class InnerFragment : Fragment() {

    private lateinit var mCounterTextView: TextView
    private lateinit var mForwardButton: Button

    private val mNavigator: Navigator = SampleApplication.navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCounterTextView = view.findViewById(R.id.counter_text_view)
        mForwardButton = view.findViewById(R.id.forward_button)

        val screen = SampleApplication.screenResolver.getScreen<InnerScreen>(this)
        val counter = screen.counter
        mCounterTextView.text = getString(R.string.counter_template, counter)
        mForwardButton.setOnClickListener { mNavigator.goForward(InnerScreen(counter + 1)) }
    }

    // Workaround for issue https://code.google.com/p/android/issues/detail?id=55228
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        val parentFragment = parentFragment
        if (parentFragment != null && (parentFragment.isDetached || parentFragment.isRemoving)) {
            return AnimationUtils.loadAnimation(context, R.anim.stay)
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }
}