package me.aartikov.simplenavigationsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.simplenavigationsample.R
import me.aartikov.simplenavigationsample.SampleApplication
import me.aartikov.simplenavigationsample.screens.ScreenC
import me.aartikov.simplenavigationsample.screens.ScreenD

@RegisterScreen(ScreenC::class)
class FragmentC : Fragment() {
    private val mNavigator = SampleApplication.navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_c, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val goForwardToDButton = view.findViewById<Button>((R.id.go_forward_to_d_button))
        goForwardToDButton.setOnClickListener { mNavigator.goForward(ScreenD("Message for D from C")) }
    }
}
